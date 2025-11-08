package com.aura.service.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class MockAnalyticsService implements AnalyticsService {
    
    private final Random random = new Random();
    
    @Override
    public Double getBoxOfficePrediction(Long movieId) {
        return 50000000.0 + (random.nextDouble() * 100000000.0);
    }
    
    @Override
    public String getTrendingGenre(LocalDate date) {
        String[] genres = {"Action", "Comedy", "Drama", "Thriller", "Sci-Fi", "Romance"};
        return genres[random.nextInt(genres.length)];
    }
    
    @Override
    public String getHitGenrePrediction() {
        String[] genres = {"Action", "Comedy", "Drama", "Thriller", "Sci-Fi", "Romance"};
        return genres[random.nextInt(genres.length)];
    }
    
    @Override
    public String getBestGenre(LocalDate date) {
        String[] genres = {"Action", "Comedy", "Drama", "Thriller", "Sci-Fi", "Romance"};
        return genres[random.nextInt(genres.length)];
    }
    
    @Override
    public String getTopBoxOffice(LocalDate date) {
        String[] movies = {"Avatar 3", "Fast & Furious 15", "Marvel's Next Big Thing", "The Sequel Nobody Asked For"};
        return movies[random.nextInt(movies.length)];
    }
}
