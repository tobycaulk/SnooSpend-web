package com.tcaulk.snoospend.service.mongo;

import com.tcaulk.snoospend.model.ProductCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductCollectionRepository extends MongoRepository<ProductCollection, String> {
    Page<ProductCollection> findAll(Pageable pageable);
}