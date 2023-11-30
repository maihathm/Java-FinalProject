package com.example.midterm.controller;

import com.example.midterm.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/")
    public String home(Model model) throws Exception {
        model.addAttribute("hotProducts", productService.getAllHotProducts());
        model.addAttribute("allBrands", brandService.getAllBrand());
        model.addAttribute("allCategories", categoryService.getAllCategories());

        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model) throws Exception {
        model.addAttribute("allCategories",  categoryService.getAllCategories());
        model.addAttribute("allBrands", brandService.getAllBrand());

        return "shop";
    }

    @GetMapping("/brand")
    public String brand(
            @RequestParam(value = "id", required = false) String id,
            Model model
    ) throws Exception {
        model.addAttribute("allCategories",  categoryService.getAllCategories());
        model.addAttribute("allBrands", brandService.getAllBrand());

        if (id != null && !id.isEmpty()) {
            model.addAttribute("brand", brandService.getBrandById(Long.valueOf(id)));
            return "brand";
        }

        return "brand";
    }

    @GetMapping("/category")
    public String category(
            @RequestParam(value = "id", required = false) String id,
            Model model
    ) throws Exception {
        model.addAttribute("allCategories",  categoryService.getAllCategories());
        model.addAttribute("allBrands", brandService.getAllBrand());

        if (id != null && !id.isEmpty()) {
            model.addAttribute("category", categoryService.getCategoryById(Long.valueOf(id)));
            return "category";
        }

        return "category";
    }
}
