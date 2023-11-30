package com.example.midterm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long quantity = 0L;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }
}
