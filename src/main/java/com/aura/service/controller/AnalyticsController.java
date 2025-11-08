package com.aura.service.controller;

import com.aura.service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
    
    @GetMapping("/box-office-prediction")
    public ResponseEntity<Map<String, Object>> getBoxOfficePrediction(@RequestParam Long movieId) {
        Double prediction = analyticsService.getBoxOfficePrediction(movieId);
        Map<String, Object> response = new HashMap<>();
        response.put("movieId", movieId);
        response.put("predictedBoxOffice", prediction);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/trending-genre")
    public ResponseEntity<Map<String, String>> getTrendingGenre(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        String genre = analyticsService.getTrendingGenre(date);
        Map<String, String> response = new HashMap<>();
        response.put("date", date.toString());
        response.put("trendingGenre", genre);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/hit-genre-prediction")
    public ResponseEntity<Map<String, String>> getHitGenrePrediction() {
        String genre = analyticsService.getHitGenrePrediction();
        Map<String, String> response = new HashMap<>();
        response.put("predictedHitGenre", genre);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/best-genre")
    public ResponseEntity<Map<String, String>> getBestGenre(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        String genre = analyticsService.getBestGenre(date);
        Map<String, String> response = new HashMap<>();
        response.put("date", date.toString());
        response.put("bestGenre", genre);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/top-box-office")
    public ResponseEntity<Map<String, String>> getTopBoxOffice(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        String topMovie = analyticsService.getTopBoxOffice(date);
        Map<String, String> response = new HashMap<>();
        response.put("date", date.toString());
        response.put("topBoxOfficeMovie", topMovie);
        return ResponseEntity.ok(response);
    }
}
