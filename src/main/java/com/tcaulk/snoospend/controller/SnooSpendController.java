package com.tcaulk.snoospend.controller;

import com.tcaulk.snoospend.model.display.DisplayProductCollection;
import com.tcaulk.snoospend.service.SnooSpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;

@Controller
public class SnooSpendController {

    private SnooSpendService snooSpendService;

    @Autowired
    public SnooSpendController(SnooSpendService snooSpendService) {
        this.snooSpendService = snooSpendService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:page/1";
    }

    @GetMapping("/page/{page}")
    public String getPage(@PathVariable("page") int page, Model model) {
        if(page == 0) {
            return "redirect:1";
        }

        model.addAttribute("products", snooSpendService.getProductPage(page < 0 ? 0 : page >= 1 ? page - 1 : page));
        model.addAttribute("productCount", snooSpendService.getProductCount());
        model.addAttribute("pages", page > 1 ? Arrays.asList(page - 1, page + 1) : Arrays.asList(page + 1));
        model.addAttribute("productCollections", snooSpendService.getDisplayProductCollectionPage(0, 4));

        return "index";
    }

    @GetMapping("/product/{asin}")
    public String getProductDetail(@PathVariable("asin") String asin, Model model) {
        model.addAttribute("product", snooSpendService.getProductDetail(asin));

        return "product";
    }

    @GetMapping("/search")
    public String getSearch(String query, int page, Model model) {
        model.addAttribute("products", snooSpendService.searchProducts(query, page < 0 ? 0 : page));
        model.addAttribute("query", query);

        return "searchresult";
    }

    @GetMapping("/subscribeNewsletter")
    public String subscribeNewsletter(String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("success", snooSpendService.subscribeNewsletter(email));

        return "subscribe";
    }

    @GetMapping("/collection/{productCollectionId}")
    public String getCollection(@PathVariable("productCollectionId") String productCollectionId, Model model) {
        DisplayProductCollection collection = snooSpendService.getCollection(productCollectionId);
        model.addAttribute("name", collection.getName());
        model.addAttribute("description", collection.getDescription());
        model.addAttribute("products", collection.getDisplayProducts());
        model.addAttribute("subreddits", collection.getSubreddits());

        return "collection";
    }

    @GetMapping("/statistics")
    public String getStatistics() {
        return "statistics";
    }
}