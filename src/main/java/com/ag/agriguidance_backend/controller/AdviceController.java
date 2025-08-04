package com.ag.agriguidance_backend.controller;

import com.ag.agriguidance_backend.dto.AdviceRequest;
import com.ag.agriguidance_backend.model.Product;
import com.ag.agriguidance_backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advice")
@CrossOrigin("*")
public class AdviceController {

    private final ProductService productService;

    public AdviceController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public String getAdvice(@RequestBody AdviceRequest request) {
        List<Product> products = productService.getAllProducts();
        String description = request.getProblemDescription().toLowerCase();

        for (Product product : products) {
            if (product.getStockQuantity() > 0 &&
                    product.getDiseasesTreated().toLowerCase().contains("black") &&
                    description.contains("black")) {

                return "Recommended: " + product.getName() + "\n"
                        + "How to use: " + product.getUsageInstructions();
            }
        }

        return "No suitable medicine found in stock.";
    }
}
