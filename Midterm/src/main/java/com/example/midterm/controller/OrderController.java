package com.example.midterm.controller;

import com.example.midterm.model.OrderDetail;
import com.example.midterm.model.Order;
import com.example.midterm.services.OrderService;
import com.example.midterm.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderController(OrderService orderService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public String viewOrderPage(Model model) {
        model.addAttribute("allOrders",orderService.getAllOrders());

        return "pages/list_order";
    }
    @RequestMapping("/detail/{id}")
    public String viewOrderDetailPage(@PathVariable(name = "id") long id,Model model) {
        List<OrderDetail> orderDetail = orderDetailService.getOrderDetail(id);
        model.addAttribute("orderDetail", orderDetail);
        return "pages/list_orderDetail";
    }
    @GetMapping("/revenue")
    public String getTotalRevenueForMonthAndYear(
            @RequestParam(name = "year", required = true) int year,
            @RequestParam(name = "month", required = true) int month,
            Model model) {

        double totalRevenue = orderService.getTotalRevenueForMonthAndYear(year, month);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("year", year);
        model.addAttribute("month", month);

        return "pages/revenue";
    }
}
