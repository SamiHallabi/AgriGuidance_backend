package com.ag.agriguidance_backend.service;

import com.ag.agriguidance_backend.dto.RecommendationRequestDTO;
import com.ag.agriguidance_backend.model.Product;
import com.ag.agriguidance_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class AiService {

    @Autowired
    private ProductRepository productRepository;

    private final String API_URL = "https://api.together.xyz/v1/chat/completions";
    private final String API_KEY = "401ce1b8b2fab3633968c760de2ad48e7e8302410dff94ffcf6cea5f68d94f12";

    private final RestTemplate restTemplate = new RestTemplate();

    public String askAi(RecommendationRequestDTO dto) {
        List<Product> availableProducts = productRepository.findByStockQuantityGreaterThan(0);

        StringBuilder stockList = new StringBuilder();
        for (Product product : availableProducts) {
            stockList.append("- ")
                    .append(product.getName())
                    .append(": ")
                    .append(product.getDescription())
                    .append(" (Stock: ")
                    .append(product.getStockQuantity())
                    .append(")\n");
        }

        String prompt = String.format(
                "You're an agriculture assistant AI.\n" +
                        "A store has the following medicines:\n%s\n\n" +
                        "A seller submitted this plant case:\n" +
                        "- Plant: %s\n" +
                        "- Surface: %.2f m²\n" +
                        "- Problem: %s\n\n" +
                        "Based on the available stock, recommend one or more products that can help. " +
                        "Explain clearly how and when to use each recommended product. If no suitable product exists, say so.",
                stockList.toString(),
                dto.getPlantName(),
                dto.getSurface(),
                dto.getProblemDescription()
        );

        // Prepare request (same as before)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "mistralai/Mixtral-8x7B-Instruct-v0.1");
        body.put("messages", List.of(
                Map.of("role", "system", "content", "You're an expert in agriculture and only recommend products from the given stock."),
                Map.of("role", "user", "content", prompt)
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);

        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            return (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}

