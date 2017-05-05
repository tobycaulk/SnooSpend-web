package com.tcaulk.snoospend.service.mongo;

import com.tcaulk.snoospend.model.ProductCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductCollectionRepository extends MongoRepository<ProductCollection, String> {
}