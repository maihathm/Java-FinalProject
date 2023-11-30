package com.example.midterm.repos;

import com.example.midterm.model.Brand;
import com.example.midterm.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category save(Category category);

    void deleteById(Long id);

    List<Category> findAllByOrderByName();
}
