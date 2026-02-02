package com.aura.service.controller;

import com.aura.service.service.AnalyticsService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Data
    static class AnalyticsRequest {
        private Long movieId;
        private LocalDate date;
        private int sentimentScore;
        private double positivityRatio;
    }

    @PostMapping("/box-office-prediction")
    public ResponseEntity<Map<String, Object>> getBoxOfficePrediction(@RequestBody AnalyticsRequest request) {
        analyticsService.init();
        JsonNode prediction = analyticsService.getBoxOfficePrediction(request.getMovieId(), request.getDate(), request.getSentimentScore(), request.getPositivityRatio());
        Map<String, Object> response = new HashMap<>();
        response.put("movieId", request.getMovieId());
        response.put("predictedBoxOffice", prediction);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/trending-genre")
    public ResponseEntity<Map<String, Object>> getTrendingGenre(@RequestBody AnalyticsRequest request) {
        analyticsService.init();
        JsonNode genre = analyticsService.getTrendingGenre(request.getMovieId(), request.getDate(), request.getSentimentScore(), request.getPositivityRatio());
        Map<String, Object> response = new HashMap<>();
        response.put("date", request.getDate().toString());
        response.put("trendingGenre", genre);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/hit-genre-prediction")
    public ResponseEntity<Map<String, Object>> getHitGenrePrediction(@RequestBody AnalyticsRequest request) {
        analyticsService.init();
        JsonNode genre = analyticsService.getHitGenrePrediction(request.getMovieId(), request.getDate(), request.getSentimentScore(), request.getPositivityRatio());
        Map<String, Object> response = new HashMap<>();
        response.put("predictedHitGenre", genre);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/best-genre")
    public ResponseEntity<Map<String, Object>> getBestGenre(@RequestBody AnalyticsRequest request) {
        analyticsService.init();
        JsonNode genre = analyticsService.getBestGenre(request.getMovieId(), request.getDate(), request.getSentimentScore(), request.getPositivityRatio());
        Map<String, Object> response = new HashMap<>();
        response.put("date", request.getDate().toString());
        response.put("bestGenre", genre);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/top-box-office")
    public ResponseEntity<Map<String, Object>> getTopBoxOffice(@RequestBody AnalyticsRequest request) {
        analyticsService.init();
        JsonNode topMovie = analyticsService.getTopBoxOffice(request.getMovieId(), request.getDate(), request.getSentimentScore(), request.getPositivityRatio());
        Map<String, Object> response = new HashMap<>();
        response.put("date", request.getDate().toString());
        response.put("topBoxOfficeMovie", topMovie);
        return ResponseEntity.ok(response);
    }
}
