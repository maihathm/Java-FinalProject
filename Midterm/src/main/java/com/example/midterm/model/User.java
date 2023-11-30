package com.example.midterm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<CartItem> cartItems;
}
