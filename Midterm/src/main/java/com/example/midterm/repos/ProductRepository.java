package com.example.midterm.repos;

import com.example.midterm.model.Brand;
import com.example.midterm.model.Category;
import com.example.midterm.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE %:name% or LOWER(p.color) LIKE %:name% or LOWER(p.category.name) LIKE %:name% or LOWER(p.brand.name) LIKE %:name%")
    List<Product> searchAllBy(@Param("name") String name);

    List<Product> findAllByHot(boolean isHot);

    Page<Product> findByBrandId(Long brandId, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);


    Page<Product> findAll(Pageable pageable);


    @Query("SELECT p FROM Product p WHERE (LOWER(p.name) LIKE %:name% or LOWER(p.color) LIKE %:name% or LOWER(p.category.name) LIKE %:name% or LOWER(p.brand.name) LIKE %:name%) AND p.price BETWEEN :minPrice AND :maxPrice AND LOWER(p.color) LIKE %:color%")
    List<Product> searchNameFilter(@Param("name") String name, @Param("color") String color, @Param("minPrice") Long minPrice, @Param("maxPrice") Long maxPrice);

    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId AND LOWER(p.color) LIKE %:color% AND p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> searchBrandFilter(@Param("brandId") Long brandId, @Param("color") String color, @Param("minPrice") Long minPrice, @Param("maxPrice") Long maxPrice);
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryID AND p.price BETWEEN :minPrice AND :maxPrice AND LOWER(p.color) LIKE %:color%")
    List<Product> searchCategoryFilter(@Param("categoryID") Long name, @Param("color") String color, @Param("minPrice") Long minPrice, @Param("maxPrice") Long maxPrice);


    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :id")
    void deleteById(@Param("id") Long id);
}
