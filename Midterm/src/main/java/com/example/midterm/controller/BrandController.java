package com.example.midterm.controller;

import com.example.midterm.model.Brand;
import com.example.midterm.services.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/brand")
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public String viewBrandPage(Model model) {
        List<Brand> allBrands = brandService.getAllBrand();
        model.addAttribute("allBrands", allBrands);
        return "pages/list_brand";
    }

    @GetMapping("/new_brand")
    public String showNewBrandPage(Model model) {
        Brand brand = new Brand();
        model.addAttribute("brand", brand);
        return "pages/new_brand";
    }

    @PostMapping("/save")
    public String saveBrand(@ModelAttribute("brand") Brand brand) {
        brandService.saveBrand(brand);
        return "redirect:/admin/brand";
    }

    @GetMapping("/edit/{id}")
    public String showEditBrandPage(@PathVariable(name = "id") Long id, Model model) {
        Brand brand;
        try {
            brand = brandService.getBrandById(id);
        } catch (Exception e) {
            return "redirect:/admin/brand";
        }
        model.addAttribute("brand", brand);
        return "pages/edit_brand";
    }


    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Long id) {
        try {
            brandService.deleteBrand(id);
        } catch (Exception e) {
            // Handle exception (e.g., brand not found)
        }
        return "redirect:/admin/brand";
    }
}
