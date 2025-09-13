package com.ag.agriguidance_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @JsonProperty("Description")
    @Column(length = 3000)
    private String description;
    @JsonProperty("Composition")
    private String composition;
    @JsonProperty("Emballage")
    private String emballage;


}
