package com.ag.agriguidance_backend.repository;

import com.ag.agriguidance_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContainingIgnoreCase(String title);
    List<Product> findByDescriptionContainingIgnoreCase(String description);
    List<Product> findByCompositionContainingIgnoreCase(String composition);
    List<Product> findByEmballageContainingIgnoreCase(String emballage);




}
