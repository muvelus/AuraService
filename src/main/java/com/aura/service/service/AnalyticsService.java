package com.aura.service.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface AnalyticsService {
    void init();
    JsonNode getAnalytics(Long movieId);
}
