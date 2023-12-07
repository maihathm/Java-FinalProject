package com.example.midterm.repos;

import com.example.midterm.model.Order;
import com.example.midterm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();


    List<Order> findByUser(User user);
    Optional<Order> findById(Long id);
    @Query("SELECT COUNT(o) FROM Order o WHERE MONTH(o.orderDate) = :month")
    long countByMonth(@Param("month") int month);

}