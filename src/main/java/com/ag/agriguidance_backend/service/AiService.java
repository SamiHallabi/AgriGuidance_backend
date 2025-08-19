package com.ag.agriguidance_backend.service;

import com.ag.agriguidance_backend.dto.RecommendationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import com.ag.agriguidance_backend.model.Product;
import com.ag.agriguidance_backend.repository.ProductRepository;

import java.util.List;

@Service
public class AiService {

    @Autowired
    private ProductRepository productRepository;

    private final String API_URL = "https://api.together.xyz/v1/chat/completions";
    private final String API_KEY = "401ce1b8b2fab3633968c760de2ad48e7e8302410dff94ffcf6cea5f68d94f12";

    private final RestTemplate restTemplate = new RestTemplate();

    public String getRecommendationText(RecommendationRequestDTO dto) {
        List<Product> availableProducts = productRepository.findAll();

        StringBuilder stockList = new StringBuilder();
        for (Product product : availableProducts) {
            stockList.append("- ID: ")
                    .append(product.getId())
                    .append(", Name: ")
                    .append(product.getTitle())
                    .append(", Description: ")
                    .append(product.getDescription())
                    .append("\n");
        }

        String prompt = String.format(
                "You're an agriculture assistant AI.\n" +
                        "A store has the following medicines:\n%s\n\n" +
                        "A seller submitted this plant case:\n" +
                        "- Plant: %s\n" +
                        "- Surface: %.2f m²\n" +
                        "- Problem: %s\n\n" +
                        "👉 Your task: Return plain text advice for the seller, listing products and usage clearly.\n" +
                        "Do NOT return JSON, only text instructions.",
                stockList.toString(),
                dto.getPlantName(),
                dto.getSurface(),
                dto.getProblemDescription()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        // Request body
        var body = new java.util.HashMap<String, Object>();
        body.put("model", "mistralai/Mixtral-8x7B-Instruct-v0.1");
        body.put("messages", List.of(
                java.util.Map.of("role", "system", "content", "You're an expert in agriculture."),
                java.util.Map.of("role", "user", "content", prompt)
        ));

        HttpEntity<java.util.Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<java.util.Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, java.util.Map.class);
            List<java.util.Map<String, Object>> choices = (List<java.util.Map<String, Object>>) response.getBody().get("choices");
            return (String) ((java.util.Map<String, Object>) choices.get(0).get("message")).get("content");

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
