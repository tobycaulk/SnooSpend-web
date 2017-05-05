package com.tcaulk.snoospend.service.mongo;

import com.tcaulk.snoospend.model.NewsletterEmail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<NewsletterEmail, String> {
    NewsletterEmail findByEmail(String email);
}