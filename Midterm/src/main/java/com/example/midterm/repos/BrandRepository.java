package com.example.midterm.repos;

import com.example.midterm.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findAll();

    Optional<Brand> findById(Long id);

    Brand save(Brand brand);

    void deleteById(Long id);

    List<Brand> findAllByOrderByName();

}
