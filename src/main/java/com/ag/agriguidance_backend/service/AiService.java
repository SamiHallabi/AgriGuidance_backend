package com.ag.agriguidance_backend.service;

import com.ag.agriguidance_backend.dto.RecommendationRequestDTO;
import com.ag.agriguidance_backend.model.Product;
import com.ag.agriguidance_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    @Autowired
    private ProductRepository productRepository;

    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=YOUR_API_KEY";
    private final String API_KEY = "AIzaSyADXNarOg67X8JpaLn_tSpMmwdW6uwoKgk"; // ⚠️ better move this to config/env

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
                        "👉 Your task: in arabic Write a clear and practical recommendation for the seller.\n" +
                        "\n" +
                        "Rules:\n" +
                        "- Be short and to the point.\n" +
                        "- Use a simple list format.\n" +
                        "- Recommend the best 1–3 products from the stock.\n" +
                        "- For each product, include:\n" +
                        "  • Product name  \n" +
                        "  • How to use it (dosage + method)  \n" +
                        "  • Timing (when and how often)  \n" +
                        "- End with one quick tip for the seller.\n" +
                        "\n" +
                        "Do NOT write in JSON, just plain text.",
                stockList.toString(),
                dto.getPlantName(),
                dto.getSurface(),
                dto.getProblemDescription()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Gemini expects "contents" not "messages"
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    API_URL.replace("YOUR_API_KEY", API_KEY),
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            // Gemini returns candidates[0].content.parts[0].text
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                return (String) parts.get(0).get("text");
            } else {
                return "No response from AI.";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
