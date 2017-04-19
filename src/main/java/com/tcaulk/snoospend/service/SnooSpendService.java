package com.tcaulk.snoospend.service;

import com.tcaulk.snoospend.model.Product;
import com.tcaulk.snoospend.service.mongo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SnooSpendService {

    private static final int PRODUCT_PAGE_SIZE = 25;

    private ProductRepository productRepository;

    @Autowired
    public SnooSpendService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductPage(int page) {
        PageRequest pageRequest = new PageRequest(page, PRODUCT_PAGE_SIZE, new Sort(Sort.Direction.DESC, "popularity"));
        Page<Product> pageResponse = productRepository.findByProcessed(true, pageRequest);

        return pageResponse.getContent();
    }
}