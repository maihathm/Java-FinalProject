package com.example.midterm.repos;

import com.example.midterm.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();
    List<Order> findByOrderDateBetween(Date startDate, Date endDate);
}