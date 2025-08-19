package com.ag.agriguidance_backend.config;

import com.ag.agriguidance_backend.model.Product;
import com.ag.agriguidance_backend.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) { // load only if DB is empty
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = getClass().getResourceAsStream("/products.json");
            if (inputStream == null) {
                throw new RuntimeException("products.json not found in resources");
            }

            List<Product> products = mapper.readValue(
                    inputStream,
                    new TypeReference<List<Product>>() {}
            );

            // Ensure IDs are null so DB generates them
            products.forEach(p -> p.setId(null));

            productRepository.saveAll(products);
            System.out.println("Products loaded from JSON into database.");
        }
    }
}
