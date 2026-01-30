package com.aura.service.service;

import com.aura.service.dto.*;
import com.aura.service.entity.ManagedEntity;
import com.aura.service.entity.Mention;
import com.aura.service.enums.Platform;
import com.aura.service.enums.Sentiment;
import com.aura.service.enums.TimePeriod;
import com.aura.service.repository.ManagedEntityRepository;
import com.aura.service.repository.MentionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final MentionRepository mentionRepository;
    private final ManagedEntityRepository entityRepository;
    
    public EntityStatsResponse getEntityStats(Long entityId) {
        long totalMentions = mentionRepository.countByManagedEntityId(entityId);
        long positiveMentions = mentionRepository.countByManagedEntityIdAndSentiment(entityId, Sentiment.POSITIVE);
        long negativeMentions = mentionRepository.countByManagedEntityIdAndSentiment(entityId, Sentiment.NEGATIVE);
        
        double positiveSentiment = totalMentions > 0 ? (double) positiveMentions / totalMentions : 0.0;
        double negativeSentiment = totalMentions > 0 ? (double) negativeMentions / totalMentions : 0.0;
        
        return new EntityStatsResponse(totalMentions, positiveSentiment, negativeSentiment);
    }
    
    public List<CompetitorSnapshot> getCompetitorSnapshot(Long entityId) {
        ManagedEntity entity = entityRepository.findById(entityId)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + entityId));
        
        List<CompetitorSnapshot> snapshots = new ArrayList<>();
        
        snapshots.add(createSnapshot(entity));
        
        for (ManagedEntity competitor : entity.getCompetitors()) {
            snapshots.add(createSnapshot(competitor));
        }
        
        return snapshots;
    }
    
    private CompetitorSnapshot createSnapshot(ManagedEntity entity) {
        long totalMentions = mentionRepository.countByManagedEntityId(entity.getId());
        long positiveMentions = mentionRepository.countByManagedEntityIdAndSentiment(entity.getId(), Sentiment.POSITIVE);
        
        double positiveSentiment = totalMentions > 0 ? (double) positiveMentions / totalMentions : 0.0;
        
        return new CompetitorSnapshot(entity.getName(), totalMentions, positiveSentiment);
    }
    
    public SentimentOverTimeResponse getSentimentOverTime(
            TimePeriod period,
            List<Long> entityIds
    ) {
        List<EntitySentimentData> entitySentiments = new ArrayList<>();
        
        Instant endDate = Instant.now();
        Instant startDate = calculateStartDate(period, endDate);
        
        for (Long entityId : entityIds) {
            ManagedEntity entity = entityRepository.findById(entityId)
                    .orElseThrow(() -> new RuntimeException("Entity not found with id: " + entityId));
            
            List<Mention> mentions = mentionRepository.findByEntityIdsAndDateRange(
                    Collections.singletonList(entityId),
                    startDate,
                    endDate
            );
            
            List<TimeSeriesData> timeSeriesData = aggregateMentionsByPeriod(mentions, period, startDate, endDate);
            entitySentiments.add(new EntitySentimentData(entity.getName(), timeSeriesData));
        }
        
        return new SentimentOverTimeResponse(entitySentiments);
    }
    
    private Instant calculateStartDate(TimePeriod period, Instant endDate) {
        ZonedDateTime zonedDateTime = endDate.atZone(ZoneId.systemDefault());
        return switch (period) {
            case DAY -> endDate.minus(7, ChronoUnit.DAYS);
            case WEEK -> zonedDateTime.minusWeeks(8).toInstant();
            case MONTH -> zonedDateTime.minusMonths(12).toInstant();
        };
    }
    
    private List<TimeSeriesData> aggregateMentionsByPeriod(
            List<Mention> mentions,
            TimePeriod period,
            Instant startDate,
            Instant endDate
    ) {
        Map<String, TimeSeriesData> dataMap = new LinkedHashMap<>();
        
        Instant current = startDate;
        DateTimeFormatter formatter = getFormatterForPeriod(period);
        
        while (current.isBefore(endDate) || current.equals(endDate)) {
            String dateKey = formatDate(current, formatter);
            dataMap.put(dateKey, new TimeSeriesData(dateKey, 0, 0));
            current = incrementByPeriod(current, period);
        }
        
        for (Mention mention : mentions) {
            String dateKey = formatDate(mention.getPostDate(), formatter);
            TimeSeriesData data = dataMap.get(dateKey);
            if (data != null) {
                if (mention.getSentiment() == Sentiment.POSITIVE) {
                    data.setPositive(data.getPositive() + 1);
                } else if (mention.getSentiment() == Sentiment.NEGATIVE) {
                    data.setNegative(data.getNegative() + 1);
                }
            }
        }
        
        return new ArrayList<>(dataMap.values());
    }
    
    private DateTimeFormatter getFormatterForPeriod(TimePeriod period) {
        return switch (period) {
            case DAY -> DateTimeFormatter.ofPattern("yyyy-MM-dd");
            case WEEK -> DateTimeFormatter.ofPattern("yyyy-'W'ww");
            case MONTH -> DateTimeFormatter.ofPattern("yyyy-MM");
        };
    }
    
    private String formatDate(Instant instant, DateTimeFormatter formatter) {
        return LocalDate.ofInstant(instant, ZoneId.systemDefault()).format(formatter);
    }
    
    private Instant incrementByPeriod(Instant instant, TimePeriod period) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return switch (period) {
            case DAY -> instant.plus(1, ChronoUnit.DAYS);
            case WEEK -> zonedDateTime.plusWeeks(1).toInstant();
            case MONTH -> zonedDateTime.plusMonths(1).toInstant();
        };
    }
    
    public Map<String, Long> getPlatformMentions(Long entityId) {
        List<Object[]> results = mentionRepository.countByPlatformForEntity(entityId);
        
        Map<String, Long> platformCounts = new HashMap<>();
        for (Object[] result : results) {
            Platform platform = (Platform) result[0];
            Long count = (Long) result[1];
            platformCounts.put(platform.name(), count);
        }
        
        return platformCounts;
    }
    
    public Page<MentionResponse> getMentions(
            Long entityId,
            Platform platform,
            String country,
            String city,
            Integer age,
            Instant startDate,
            Instant endDate,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        
        Page<Mention> mentions = mentionRepository.findFilteredMentions(
                entityId,
                platform,
                country,
                city,
                age,
                startDate,
                endDate,
                pageable
        );
        
        return mentions.map(this::mapToMentionResponse);
    }
    
    private MentionResponse mapToMentionResponse(Mention mention) {
        return new MentionResponse(
                mention.getId(),
                mention.getManagedEntity().getId(),
                mention.getPlatform(),
                mention.getPostId(),
                mention.getContent(),
                mention.getAuthor(),
                mention.getAuthorAge(),
                mention.getLocationCountry(),
                mention.getLocationCity(),
                mention.getPostDate(),
                mention.getSentiment()
        );
    }
}
