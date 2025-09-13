package com.ag.agriguidance_backend.controller;

import com.ag.agriguidance_backend.dto.RecommendationRequestDTO;
import com.ag.agriguidance_backend.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
        @RequestMapping("/api/ai")
        @CrossOrigin("*")
        public class AiController {

            @Autowired
            private AiService aiService;

            @PostMapping("/recommendation")
            public ResponseEntity<String> getRecommendation(@RequestBody RecommendationRequestDTO dto) {
                String aiText = aiService.getRecommendationText(dto);
                return ResponseEntity.ok(aiText);
            }
        }

