package com.example.midterm.controller;

import com.example.midterm.model.Product;
import com.example.midterm.repos.ProductRepository;
import com.example.midterm.services.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private final ProductService productService;

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public String viewProductPage(Model model) {
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);
        return "pages/list_product";
    }
    @GetMapping("/new_product")
    public String showNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("ListBrand",brandService.getAllBrand());
        model.addAttribute("ListCategory",categoryService.getAllCategories());
        return "pages/new_product";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);

        return "redirect:/admin/product";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("pages/edit_product");
        Product product = productService.get(id);
        mav.addObject("product", product);
        mav.addObject("ListBrand",brandService.getAllBrand());
        mav.addObject("ListCategory",categoryService.getAllCategories());
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        productService.delete(id);
        return "redirect:/admin/product";
    }
}
