package com.aura.service.controller;

import com.aura.service.dto.*;
import com.aura.service.enums.Platform;
import com.aura.service.enums.TimePeriod;
import com.aura.service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard/{entityType}/{entityId}")
@RequiredArgsConstructor
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @GetMapping("/stats")
    public ResponseEntity<EntityStatsResponse> getStats(@PathVariable String entityType, @PathVariable Long entityId) {
        EntityStatsResponse response = dashboardService.getEntityStats(entityType, entityId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/competitor-snapshot")
    public ResponseEntity<List<CompetitorSnapshot>> getCompetitorSnapshot(@PathVariable String entityType, @PathVariable Long entityId) {
        List<CompetitorSnapshot> response = dashboardService.getCompetitorSnapshot(entityType, entityId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/sentiment-over-time")
    public ResponseEntity<SentimentOverTimeResponse> getSentimentOverTime(
            @PathVariable String entityType,
            @PathVariable Long entityId,
            @RequestParam TimePeriod period,
            @RequestParam List<Long> entityIds
    ) {
        SentimentOverTimeResponse response = dashboardService.getSentimentOverTime(entityType, entityId, period, entityIds);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/platform-mentions")
    public ResponseEntity<Map<String, Map<String, Long>>> getPlatformMentions(@PathVariable String entityType, @PathVariable Long entityId) {
        Map<String, Map<String, Long>> response = dashboardService.getPlatformMentions(entityType, entityId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/mentions")
    public ResponseEntity<Page<MentionResponse>> getMentions(
            @PathVariable String entityType,
            @PathVariable Long entityId,
            @RequestParam(required = false) Platform platform,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "" + Integer.MAX_VALUE) int size
    ) {
        Page<MentionResponse> response = dashboardService.getMentions(
                entityType, entityId, platform, page, size
        );
        return ResponseEntity.ok(response);
    }
}
