package com.tcaulk.snoospend.controller;

import com.tcaulk.snoospend.service.SnooSpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("products", snooSpendService.getProductPage(page <= 1 ? 1 : page - 1));
        model.addAttribute("productCount", snooSpendService.getProductCount());
        model.addAttribute("pages", Arrays.asList(page + 1, page + 2, page + 3));

        return "index";
    }
}