package com.ag.agriguidance_backend.service;

import com.ag.agriguidance_backend.model.Product;
import com.ag.agriguidance_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public List<Product> searchByDisease(String disease) {
        return repo.findByDiseasesTreatedContainingIgnoreCase(disease);
    }

    public Product save(Product p) {
        return repo.save(p);
    }
}