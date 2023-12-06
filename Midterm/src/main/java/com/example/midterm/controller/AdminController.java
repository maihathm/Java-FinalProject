package com.example.midterm.controller;

import com.example.midterm.model.Product;
import com.example.midterm.services.OrderService;
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
    @Autowired
    private OrderService orderService;
    @GetMapping
    public String viewHomePage() {
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/dashboard")
    public String viewHomePage(Model model) {
        double currentRevenue = orderService.getCurrentMonthRevenue();
        double previousRevenue = orderService.getPreviousMonthRevenue();
        double diffRevenue;
        if (previousRevenue==0){
            diffRevenue=100;
        }
        else{
            diffRevenue=(currentRevenue-previousRevenue)/previousRevenue*100;
        }
        long numberOrder=orderService.getNumberOfOrdersForCurrentMonth();
        long numberProd=orderService.getNumberOfProductsSoldInCurrentMonth();
        model.addAttribute("currentRevenue", currentRevenue);
        model.addAttribute("diffRevenue", Math.round(diffRevenue)+"% growth");
        model.addAttribute("numberOrder", numberOrder);
        model.addAttribute("numberProd", numberProd);
        return "pages/dashboard";
    }

}
