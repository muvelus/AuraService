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
@RequestMapping("/api/dashboard/{entityId}")
@RequiredArgsConstructor
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @GetMapping("/stats")
    public ResponseEntity<EntityStatsResponse> getStats(@PathVariable Long entityId) {
        EntityStatsResponse response = dashboardService.getEntityStats(entityId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/competitor-snapshot")
    public ResponseEntity<List<CompetitorSnapshot>> getCompetitorSnapshot(@PathVariable Long entityId) {
        List<CompetitorSnapshot> response = dashboardService.getCompetitorSnapshot(entityId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/sentiment-over-time")
    public ResponseEntity<Map<String, List<TimeSeriesData>>> getSentimentOverTime(
            @PathVariable Long entityId,
            @RequestParam TimePeriod period,
            @RequestParam List<Long> entityIds
    ) {
        Map<String, List<TimeSeriesData>> response = dashboardService.getSentimentOverTime(period, entityIds);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/platform-mentions")
    public ResponseEntity<Map<String, Long>> getPlatformMentions(@PathVariable Long entityId) {
        Map<String, Long> response = dashboardService.getPlatformMentions(entityId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/mentions")
    public ResponseEntity<Page<MentionResponse>> getMentions(
            @PathVariable Long entityId,
            @RequestParam(required = false) Platform platform,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MentionResponse> response = dashboardService.getMentions(
                entityId, platform, country, city, age, startDate, endDate, page, size
        );
        return ResponseEntity.ok(response);
    }
}
