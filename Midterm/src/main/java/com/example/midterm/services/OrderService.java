package com.example.midterm.services;

import com.example.midterm.model.Order;
import com.example.midterm.model.User;
import com.example.midterm.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllByUser(String username){
        User user = userService.findByUsername(username);
        return orderRepository.findByUser(user);
    }

}
