package com.aura.service.service;

import java.time.LocalDate;

public interface AnalyticsService {
    Double getBoxOfficePrediction(Long movieId);
    String getTrendingGenre(LocalDate date);
    String getHitGenrePrediction();
    String getBestGenre(LocalDate date);
    String getTopBoxOffice(LocalDate date);
}
