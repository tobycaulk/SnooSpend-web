package com.tcaulk.snoospend.service;

import com.tcaulk.snoospend.model.ProductCollection;
import com.tcaulk.snoospend.model.display.DisplayProduct;
import com.tcaulk.snoospend.model.display.DisplayProductCollection;
import com.tcaulk.snoospend.model.NewsletterEmail;
import com.tcaulk.snoospend.model.Product;
import com.tcaulk.snoospend.service.mongo.EmailRepository;
import com.tcaulk.snoospend.service.mongo.ProductCollectionRepository;
import com.tcaulk.snoospend.service.mongo.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SnooSpendService {
    private static final Logger log = Logger.getLogger(SnooSpendService.class);

    private static final int PRODUCT_PAGE_SIZE = 10;

    private ProductRepository productRepository;
    private EmailRepository emailRepository;
    private ProductCollectionRepository productCollectionRepository;

    @Autowired
    public SnooSpendService(
            ProductRepository productRepository,
            EmailRepository emailRepository,
            ProductCollectionRepository productCollectionRepository) {

        this.productRepository = productRepository;
        this.emailRepository = emailRepository;
        this.productCollectionRepository = productCollectionRepository;
    }

    public List<DisplayProduct> getProductPage(int page) {
        List<DisplayProduct> displayProducts = new ArrayList<>();

        PageRequest pageRequest = new PageRequest(page, PRODUCT_PAGE_SIZE, new Sort(Sort.Direction.DESC, "popularity"));
        Page<Product> pageResponse = productRepository.findAll(pageRequest);
        pageResponse.getContent().forEach(product -> {
            try {
                displayProducts.add(constructDisplayProduct(product));
            } catch(Exception e) {
                log.error("Error while processing product with ASIN[" + product.getASIN() + "]", e);
            }
        });

        return displayProducts;
    }

    public long getProductCount() {
        return productRepository.count();
    }

    public DisplayProduct getProductDetail(String asin) {
        return constructDisplayProduct(productRepository.findByAsin(asin));
    }

    public List<DisplayProduct> searchProducts(String query, int page) {
        List<DisplayProduct> displayProducts = new ArrayList<>();

        //PageRequest pageRequest = new PageRequest(page, PRODUCT_PAGE_SIZE, new Sort(Sort.Direction.DESC, "popularity"));
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(query);
        List<Product> products = productRepository.findAllBy(criteria);
        products.forEach(product -> {
            try {
                displayProducts.add(constructDisplayProduct(product));
            } catch (Exception e) {
                log.error("Error while processing product with ASIN[" + product.getASIN() + "]", e);
            }
        });

        Collections.sort(displayProducts);

        return displayProducts;
    }

    public DisplayProductCollection getCollection(String productCollectionId) {
        return constructDisplayProductCollection(productCollectionRepository.findOne(productCollectionId));
    }

    public List<DisplayProductCollection> getDisplayProductCollectionPage(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);

        return productCollectionRepository.findAll(pageRequest).getContent().stream().map(collection -> {
            return constructDisplayProductCollection(collection);
        }).collect(Collectors.toList());
    }

    private DisplayProductCollection constructDisplayProductCollection(ProductCollection productCollection) {
        DisplayProductCollection collection = new DisplayProductCollection();

        List<String> subreddits = new ArrayList<>();
        List<DisplayProduct> displayProducts = new ArrayList<>();
        if(productCollection != null) {
            productCollection.getAsins().forEach(asin -> {
                Product product = productRepository.findByAsin(asin);
                if (product != null) {
                    displayProducts.add(constructDisplayProduct(product));
                    product.getSubreddits().forEach(subreddit -> {
                        if (!subreddits.contains(subreddit)) {
                            subreddits.add(subreddit);
                        }
                    });
                }
            });

            collection.setId(productCollection.getId());
            collection.setName(productCollection.getName());
            collection.setDescription(productCollection.getDescription());
            collection.setDisplayProducts(displayProducts);
            collection.setSubreddits(subreddits.stream().distinct().<String>map(subreddit -> {
                return "/r/" + subreddit;
            }).collect(Collectors.toList()));
        }

        return collection;
    }

    private DisplayProduct constructDisplayProduct(Product product) {
        DisplayProduct displayProduct = new DisplayProduct();

        if (product.getItem() != null) {
            displayProduct.setPopularity(product.getPopularity());
            displayProduct.setDetailPageUrl(product.getItem().getDetailPageURL());
            displayProduct.setImageUrl(product.getItem().getLargeImage().getURL());
            displayProduct.setProductGroup(product.getItem().getItemAttributes().getProductGroup());
            displayProduct.setTitle(product.getItem().getItemAttributes().getTitle());
            displayProduct.setDescription(product.getItem().getEditorialReviews().getEditorialReview().get(0).getContent());
            displayProduct.setAsin(product.getASIN());
            displayProduct.setSubreddits(product.getSubreddits().stream().distinct().<String>map(subreddit -> { return "/r/" + subreddit; }).collect(Collectors.toList()));
        }

        return displayProduct;
    }

    public boolean subscribeNewsletter(String email) {
        boolean success = false;

        NewsletterEmail newsletterEmail = new NewsletterEmail();
        newsletterEmail.setEmail(email);

        if(emailRepository.findByEmail(email) == null) {
            emailRepository.save(newsletterEmail);
            success = emailRepository.findByEmail(email) != null;
        }

        return success;
    }
}