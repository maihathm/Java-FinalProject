package com.example.midterm.controller;

import com.example.midterm.model.Product;
import com.example.midterm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String viewHomePage() {
        return "pages/dashboard";
    }
    @GetMapping("/dashboard")
    public String viewHomePage(Model model) {
        return "pages/dashboard";
    }
    @GetMapping("/form")
    public String viewForm(Model model) {
        return "pages/form-elements";
    }
    @GetMapping("/table")
    public String viewTable(Model model) {
        return "pages/table-elements";
    }
}
