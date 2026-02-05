package com.aura.service.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface AnalyticsService {
    JsonNode getAnalytics(Long movieId);
}
