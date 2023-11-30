package com.example.midterm.repos;

import com.example.midterm.model.Brand;
import com.example.midterm.model.Category;
import com.example.midterm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findAllByHot(boolean isHot);

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :id")
    void deleteById(@Param("id") Long id);
}
