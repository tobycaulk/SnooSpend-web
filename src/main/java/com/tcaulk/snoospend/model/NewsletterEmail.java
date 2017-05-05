package com.tcaulk.snoospend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class NewsletterEmail {

    private String id;
    private String email;

    @Id
    public String getId() {
        return id;
    }

    @JsonProperty("Email")
    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}