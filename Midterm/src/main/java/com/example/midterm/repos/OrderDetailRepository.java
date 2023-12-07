package com.example.midterm.repos;

import com.example.midterm.model.Order;
import com.example.midterm.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long id);
    @Query("SELECT COUNT(od) FROM OrderDetail od WHERE od.order IN :orders")
    long countProductsSoldInOrders(@Param("orders") List<Order> orders);
}
