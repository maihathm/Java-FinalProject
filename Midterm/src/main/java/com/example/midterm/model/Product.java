package com.example.midterm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Product {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column
    private String color;

    @Column
    private Long quantity;

    @Column
    private boolean hot;

    @Column
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", quantity=" + quantity +
                ", hot=" + hot +
                ", image='" + image + '\'' +
                ", brand=" + brand.getName() +
                ", category=" + category.getName() +
                '}';
    }
}
