package com.tcaulk.snoospend.service.mongo;

import com.tcaulk.snoospend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, String> {

    Page<Product> findByProcessed(boolean processed, Pageable pageable);

}