package com.example.midterm.services;

import com.example.midterm.model.Brand;
import com.example.midterm.repos.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id) throws Exception {
        return brandRepository.findById(id).orElseThrow(() -> new Exception("Not found Brand"));
    }
    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    public void updateBrand(Long id, Brand updatedBrand) throws Exception {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new Exception("Not found Brand"));

        // Update relevant fields
        brand.setName(updatedBrand.getName());

        // Save the updated brand
        brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

}
