package com.tcaulk.snoospend.controller;

import com.tcaulk.snoospend.service.SnooSpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SnooSpendController {

    private SnooSpendService snooSpendService;

    @Autowired
    public SnooSpendController(SnooSpendService snooSpendService) {
        this.snooSpendService = snooSpendService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", snooSpendService.getProductPage(0));

        return "index";
    }
}