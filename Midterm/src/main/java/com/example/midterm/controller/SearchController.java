package com.example.midterm.controller;

import com.example.midterm.model.CartItem;
import com.example.midterm.model.User;
import com.example.midterm.repos.CartItemRepository;
import com.example.midterm.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class SearchController {
    private final ProductService productService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final CartItemRepository cartItemRepository;
    private final CartItemSerivce cartItemSerivce;

    @GetMapping("/search")
    public String search(Principal principal,
            @RequestParam(value = "name") String name,
            Model model
    ) throws Exception {
        if(principal != null){
            List<CartItem> list = cartItemSerivce.getCartItemByUser(principal.getName());
            model.addAttribute("cartItems",list);
            model.addAttribute("user",principal.getName());
        }
        model.addAttribute("allCategories",  categoryService.getAllCategories());
        model.addAttribute("allBrands", brandService.getAllBrand());
        model.addAttribute("result", productService.searchProductsByMultiData(name));
        model.addAttribute("productName",name);
        return "search";
    }

    @GetMapping("/filter")
    public String filter(Principal principal,
            @RequestParam(value = "productName") String name,
            @RequestParam(value = "productColor") String color,
            @RequestParam(value = "productPriceRange") String priceRange,
            @RequestParam(value = "productBrandId") String brandId,
            @RequestParam(value = "productCategoryId") String categoryId,
            Model model
    ) throws Exception {
        if(priceRange.isEmpty()){
            priceRange = "0-99999999";
        }
        if(principal != null){
            List<CartItem> list = cartItemSerivce.getCartItemByUser(principal.getName());
            model.addAttribute("cartItems",list);
            model.addAttribute("user",principal.getName());
        }
        model.addAttribute("productPriceRange",priceRange);
        model.addAttribute("productColor",color);
        model.addAttribute("allCategories",  categoryService.getAllCategories());
        model.addAttribute("allBrands", brandService.getAllBrand());
        if(!brandId.isEmpty()){
            model.addAttribute("result", productService.searchProductsByBrandFilter(Long.valueOf(brandId),color,priceRange));
            model.addAttribute("productBrandId", brandId);
        }
        else if(!categoryId.isEmpty()){
            model.addAttribute("result", productService.searchProductsByCategoryFilter(Long.valueOf(categoryId),color,priceRange));
            model.addAttribute("productCategoryId", categoryId);

        }
        else{
            model.addAttribute("result", productService.searchProductsByNameFilter(name,color,priceRange));
            model.addAttribute("productName", name);
        }
        return "search";
    }

}
