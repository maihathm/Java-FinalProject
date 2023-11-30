package com.example.midterm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Category {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Product> products = new LinkedHashSet<>();
}
