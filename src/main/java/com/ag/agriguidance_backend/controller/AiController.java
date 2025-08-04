package com.ag.agriguidance_backend.controller;

import com.ag.agriguidance_backend.dto.RecommendationRequestDTO;
import com.ag.agriguidance_backend.service.AiService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


        @RestController
        @RequestMapping("/api/ai")
        @CrossOrigin("*")
        public class AiController {

            private final AiService aiService;

            public AiController(AiService aiService) {
                this.aiService = aiService;
            }

            @PostMapping("/recommend")
            public String recommend(@RequestBody RecommendationRequestDTO dto) {
                return aiService.askAi(dto);
            }

        }

