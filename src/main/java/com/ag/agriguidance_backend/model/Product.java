package com.ag.agriguidance_backend.model;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private String diseasesTreated;

    @Column(length = 1000)
    private String usageInstructions;

    private int stockQuantity;

    // --- Constructors ---

    public Product() {}

    public Product(int stockQuantity, String usageInstructions, String diseasesTreated, String description, String name, Long id) {
        this.stockQuantity = stockQuantity;
        this.usageInstructions = usageInstructions;
        this.diseasesTreated = diseasesTreated;
        this.description = description;
        this.name = name;
        this.id = id;
    }


    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiseasesTreated() {
        return diseasesTreated;
    }

    public void setDiseasesTreated(String diseasesTreated) {
        this.diseasesTreated = diseasesTreated;
    }

    public String getUsageInstructions() {
        return usageInstructions;
    }

    public void setUsageInstructions(String usageInstructions) {
        this.usageInstructions = usageInstructions;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

}
