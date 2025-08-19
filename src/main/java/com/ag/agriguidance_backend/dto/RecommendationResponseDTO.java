package com.ag.agriguidance_backend.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecommendationResponseDTO {
    private List<Map<String, Object>> recommendations;
    private String error;

    public RecommendationResponseDTO(List<Map<String, Object>> recommendations, String error) {
        this.recommendations = recommendations;
        this.error = error;
    }

    public List<Map<String, Object>> getRecommendations() {
        return recommendations;
    }

    public String getError() {
        return error;
    }

    public static RecommendationResponseDTO fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(json, new TypeReference<>() {});
            return new RecommendationResponseDTO(list, null);
        } catch (Exception e) {
            return new RecommendationResponseDTO(Collections.emptyList(), "Invalid JSON from AI: " + e.getMessage());
        }
    }
}
