package com.aura.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitorSnapshot {
    private String entityName;
    private long totalMentions;
    private double positiveSentiment;
}
