package com.example.midterm.controller;

import com.example.midterm.model.Order;
import com.example.midterm.model.OrderDetail;
import com.example.midterm.services.OrderDetailService;
import com.example.midterm.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    

    @GetMapping
    public String viewOrderPage(
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "year", required = false) Integer year,
            Model model
    ) {
        List<Order> orders;

        if (month != null && year != null) {
            // Both month and year are specified, filter accordingly
            orders = orderService.getOrdersByMonthAndYear(month, year);
        } else if (year != null) {
            // Only year is specified, filter by year
            orders = orderService.getOrdersByYear(year);
        } else {
            // No filters specified, get all orders
            orders = orderService.getAllOrders();
        }

        model.addAttribute("allOrders", orders);
        AtomicReference<Long> total = new AtomicReference<>(0L);
        orders.forEach(order -> {
            total.updateAndGet(v -> v + order.getTotal());
        });
        model.addAttribute("total", total.get());
        return "pages/list_order";
    }
    @RequestMapping("/detail/{id}")
    public String viewOrderDetailPage(@PathVariable(name = "id") long id, Model model) {
        List<OrderDetail> orderDetail = orderDetailService.getOrderDetail(id);
        Optional<Order> order = orderService.getOrderById(id);
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("total", order.get().getTotal());
        return "pages/list_orderDetail";
    }

}
