package com.tcaulk.snoospend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.List;

public class ProductCollection {

    private String id;
    private String name;
    private String description;
    private List<String> asins;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("asins")
    public List<String> getAsins() {
        return asins;
    }

    public void setAsins(List<String> asins) {
        this.asins = asins;
    }
}