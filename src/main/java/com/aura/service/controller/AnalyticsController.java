package com.aura.service.controller;

import com.aura.service.service.AnalyticsService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{movieId}")
    public ResponseEntity<Map<String, Object>> getPrediction(@PathVariable Long movieId) {
        analyticsService.init();
        JsonNode prediction = analyticsService.getAnalytics(movieId);
        Map<String, Object> response = new HashMap<>();
        response.put("movieId", movieId);
        response.put("predictedBoxOffice", prediction);
        return ResponseEntity.ok(response);
    }
}
