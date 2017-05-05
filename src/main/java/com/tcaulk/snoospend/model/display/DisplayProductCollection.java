package com.tcaulk.snoospend.model.display;

import com.tcaulk.snoospend.model.display.DisplayProduct;

import java.util.List;

public class DisplayProductCollection {

    private String name;
    private String description;
    private List<DisplayProduct> displayProducts;
    private List<String> subreddits;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DisplayProduct> getDisplayProducts() {
        return displayProducts;
    }

    public void setDisplayProducts(List<DisplayProduct> displayProducts) {
        this.displayProducts = displayProducts;
    }

    public List<String> getSubreddits() {
        return subreddits;
    }

    public void setSubreddits(List<String> subreddits) {
        this.subreddits = subreddits;
    }
}