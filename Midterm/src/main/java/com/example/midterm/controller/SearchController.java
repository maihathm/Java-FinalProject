package com.example.midterm.controller;

import com.example.midterm.model.User;
import com.example.midterm.services.ProductService;
import com.example.midterm.services.ShoppingCartService;
import com.example.midterm.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class SearchController {
    private final ProductService productService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "name") String name,
            Model model
    ) throws Exception {
        model.addAttribute("result", productService.searchProductsByName(name));


        return "search";
    }
}
