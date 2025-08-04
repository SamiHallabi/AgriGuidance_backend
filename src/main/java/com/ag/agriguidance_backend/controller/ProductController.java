package com.ag.agriguidance_backend.controller;

import com.ag.agriguidance_backend.model.Product;
import com.ag.agriguidance_backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String disease) {
        return service.searchByDisease(disease);
    }

    @PostMapping
    public Product add(@RequestBody Product product) {
        return service.save(product);
    }
}