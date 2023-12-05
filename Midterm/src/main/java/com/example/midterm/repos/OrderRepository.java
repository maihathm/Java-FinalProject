package com.example.midterm.repos;

import com.example.midterm.model.Order;
import com.example.midterm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();

    List<Order> findByUser(User user);
}