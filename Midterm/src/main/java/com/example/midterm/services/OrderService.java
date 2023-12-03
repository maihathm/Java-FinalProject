package com.example.midterm.services;

import com.example.midterm.model.Order;
import com.example.midterm.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public double getTotalRevenueForMonthAndYear(int year, int month) {
        // Calculate the start and end date of the specified month and year
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // Month is 0-based in Java Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);

        // Calculate total revenue from the retrieved orders
        double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();

        return totalRevenue;
    }
}
