package com.example.midterm.services;

import com.example.midterm.model.Order;
import com.example.midterm.repos.OrderDetailRepository;
import com.example.midterm.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Optional<Order> getOrderById(Long id){ return orderRepository.findById(id);}
    public List<Order> getOrdersByMonthAndYear(int month, int year) {
        List<Order> allOrders = getAllOrders();
        List<Order> filteredOrders = new ArrayList<>();

        for (Order order : allOrders) {
            Calendar orderCalendar = Calendar.getInstance();
            orderCalendar.setTime(order.getOrderDate());

            int orderMonth = orderCalendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
            int orderYear = orderCalendar.get(Calendar.YEAR);

            if (orderMonth == month && orderYear == year) {
                filteredOrders.add(order);
            }
        }

        return filteredOrders;
    }
    public List<Order> getOrdersByYear(int year) {
        List<Order> allOrders = getAllOrders();
        List<Order> filteredOrders = new ArrayList<>();

        for (Order order : allOrders) {
            Calendar orderCalendar = Calendar.getInstance();
            orderCalendar.setTime(order.getOrderDate());

            int orderYear = orderCalendar.get(Calendar.YEAR);

            if (orderYear == year) {
                filteredOrders.add(order);
            }
        }

        return filteredOrders;
    }
    // New method to get revenue for the current month
    public double getCurrentMonthRevenue() {
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int currentYear = now.get(Calendar.YEAR);

        List<Order> currentMonthOrders = getOrdersByMonthAndYear(currentMonth, currentYear);

        return currentMonthOrders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    // New method to get revenue for the previous month
    public double getPreviousMonthRevenue() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, -1); // Move to the previous month
        int previousMonth = now.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int previousYear = now.get(Calendar.YEAR);

        List<Order> previousMonthOrders = getOrdersByMonthAndYear(previousMonth, previousYear);

        return previousMonthOrders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }
    public long getNumberOfOrdersForCurrentMonth() {
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based

        return orderRepository.countByMonth(currentMonth);
    }
    public long getNumberOfProductsSoldInCurrentMonth() {
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based

        List<Order> ordersInCurrentMonth = getOrdersByMonthAndYear(currentMonth, now.get(Calendar.YEAR));

        return orderDetailRepository.countProductsSoldInOrders(ordersInCurrentMonth);
    }
}
