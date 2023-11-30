package com.example.midterm.controller;

import com.example.midterm.model.Category;
import com.example.midterm.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping
    public String viewAllCategories(Model model) {
        List<Category> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        return "pages/list_category";
    }

    @GetMapping("/new_category")
    public String showNewCategoryPage(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "pages/edit_category";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryPage(@PathVariable(name = "id") Long id, Model model) {
        Optional<Category> category;
        try {
            category = categoryService.getCategoryById(id);
        } catch (Exception e) {
            return "redirect:/admin/category";
        }
        model.addAttribute("category", category);
        return "pages/edit_category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }
}
