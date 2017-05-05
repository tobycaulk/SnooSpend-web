package com.tcaulk.snoospend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ProductItem {

    @JsonProperty("LargeImage")
    public Image largeImage;

    @JsonProperty("ItemAttributes")
    public ItemAttributes itemAttributes;

    @JsonProperty("detailPageURL")
    public String detailPageUrl;

    @JsonProperty("EditorialReviews")
    public EditorialReviews editorialReviews;

    public class Image {
        @JsonProperty("URL")
        public String url;
    }

    public class ItemAttributes {
        @JsonProperty("ProductGroup")
        public String productGroup;

        @JsonProperty("Title")
        public String title;

        @JsonProperty("ListPrice")
        public ListPrice listPrice;

        public class ListPrice {
            @JsonProperty("FormattedPrice")
            public String formattedPrice;
        }
    }

    public class EditorialReviews {
        @JsonProperty("EditorialReview")
        public List<EditorialReview> editorialReview = new ArrayList<>();

        public class EditorialReview {
            @JsonProperty("Content")
            public String content;
        }
    }
}
