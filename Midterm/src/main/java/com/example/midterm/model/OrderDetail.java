package com.example.midterm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class OrderDetail {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product.getName() +
                ", order=" + order.getId() +
                '}';
    }
}
