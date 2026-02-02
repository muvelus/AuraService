package com.aura.service.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;

public interface AnalyticsService {
    void init();
    JsonNode getBoxOfficePrediction(Long movieId, LocalDate date, int sentimentScore, double positivityRatio);
    JsonNode getTrendingGenre(Long movieId, LocalDate date, int sentiment_score, double positivityRatio);
    JsonNode getHitGenrePrediction(Long movieId, LocalDate date, int sentiment_score, double positivityRatio);
    JsonNode getBestGenre(Long movieId, LocalDate date, int sentiment_score, double positivityRatio);
    JsonNode getTopBoxOffice(Long movieId, LocalDate date, int sentiment_score, double positivityRatio);
}
