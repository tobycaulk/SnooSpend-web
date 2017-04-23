package com.tcaulk.snoospend.service;

import com.tcaulk.snoospend.model.Product;
import com.tcaulk.snoospend.model.ProductItem;
import com.tcaulk.snoospend.service.mongo.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnooSpendService {
    private static final Logger log = Logger.getLogger(SnooSpendService.class);

    private static final int PRODUCT_PAGE_SIZE = 25;

    private ProductRepository productRepository;

    @Autowired
    public SnooSpendService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductPage(int page) {
        PageRequest pageRequest = new PageRequest(page, PRODUCT_PAGE_SIZE, new Sort(Sort.Direction.DESC, "popularity"));
        Page<Product> pageResponse = productRepository.findByProcessed(true, pageRequest);
        pageResponse.getContent().forEach(product -> {
            try {
                ProductItem productItem = new ProductItem();
                productItem.detailPageUrl = product.getItem().getDetailPageURL();

                ProductItem.Image largeImage = new ProductItem.Image();
                largeImage.url = product.getItem().getLargeImage().getURL();
                productItem.largeImage = largeImage;

                ProductItem.ItemAttributes itemAttributes = new ProductItem.ItemAttributes();
                itemAttributes.productGroup = product.getItem().getItemAttributes().getProductGroup();
                itemAttributes.title = product.getItem().getItemAttributes().getTitle();
                ProductItem.ItemAttributes.ListPrice listPrice = new ProductItem.ItemAttributes.ListPrice();
                listPrice.formattedPrice = product.getItem().getItemAttributes().getListPrice() != null ? product.getItem().getItemAttributes().getListPrice().getFormattedPrice() : "";
                itemAttributes.listPrice = listPrice;
                productItem.itemAttributes = itemAttributes;

                ProductItem.EditorialReviews editorialReviews = new ProductItem.EditorialReviews();
                ProductItem.EditorialReviews.EditorialReview editorialReview = new ProductItem.EditorialReviews.EditorialReview();
                editorialReview.content = product.getItem().getEditorialReviews().getEditorialReview().get(0) != null ? product.getItem().getEditorialReviews().getEditorialReview().get(0).getContent() : "";
                editorialReviews.editorialReview.add(editorialReview);
                productItem.editorialReviews = editorialReviews;

                product.setProductItem(productItem);
            } catch(Exception e) {
                //log.error("Error while processing product with ASIN[" + product.getASIN() + "]", e);
                //pageResponse.getContent().remove(pageResponse.getContent().stream().filter(errorProduct -> errorProduct.getASIN().equals(product.getASIN())).findFirst().orElseGet(() -> null));
            }
        });

        return pageResponse.getContent();
    }

    public long getProductCount() {
        return productRepository.count();
    }
}