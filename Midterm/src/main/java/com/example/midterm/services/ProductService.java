package com.example.midterm.services;

import com.example.midterm.model.Product;
import com.example.midterm.repos.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public List<Product> getAllHotProducts() {

        return productRepository.findAllByHot(true);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product get(long id) {
        return productRepository.findById(id).get();
    }

    public void delete(long id) {
        System.out.println("Deleting product with ID: " + id);
        productRepository.deleteById(id);

    }
}
