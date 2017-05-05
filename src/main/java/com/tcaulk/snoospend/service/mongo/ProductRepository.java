package com.tcaulk.snoospend.service.mongo;

import com.tcaulk.snoospend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, String> {

    Page<Product> findAll(Pageable pageable);
    Product findByAsin(String asin);
    List<Product> findAllBy(TextCriteria criteria);

}