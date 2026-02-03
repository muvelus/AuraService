package com.aura.service.repository;

import com.aura.service.dto.SentimentStats;
import com.aura.service.entity.Mention;
import com.aura.service.enums.Platform;
import com.aura.service.enums.Sentiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface MentionRepository extends JpaRepository<Mention, Long> {

    List<Mention> findByManagedEntityId(Long entityId);

    long countByManagedEntityId(Long entityId);

    long countByManagedEntityIdAndSentiment(Long entityId, Sentiment sentiment);

    @Query("SELECT m.platform, m.sentiment, COUNT(m) FROM Mention m WHERE m.managedEntity.id = :entityId GROUP BY m.platform, m.sentiment")
    List<Object[]> countByPlatformForEntity(@Param("entityId") Long entityId);

    @Query(value = "SELECT * FROM mentions m WHERE " +
           "(:entityIds IS NULL OR m.managed_entity_id IN (:entityIds)) " +
           "AND (CAST(:platform AS VARCHAR) IS NULL OR m.platform = CAST(:platform AS VARCHAR))",
           countQuery = "SELECT count(*) FROM mentions m WHERE " +
           "(:entityIds IS NULL OR m.managed_entity_id IN (:entityIds)) " +
           "AND (CAST(:platform AS VARCHAR) IS NULL OR m.platform = CAST(:platform AS VARCHAR))",
           nativeQuery = true)
    Page<Mention> findFilteredMentions(
        @Param("entityIds") List<Long> entityIds,
        @Param("platform") String platform,
        Pageable pageable
    );

    @Query("SELECT m FROM Mention m WHERE m.managedEntity.id IN :entityIds " +
           "AND m.postDate >= :startDate AND m.postDate <= :endDate")
    List<Mention> findByEntityIdsAndDateRange(
        @Param("entityIds") List<Long> entityIds,
        @Param("startDate") Instant startDate,
        @Param("endDate") Instant endDate
    );

    @Query(value = "SELECT " +
        "    AVG(sentiment_score) AS average_sentiment_score, " +
        "    CAST(COUNT(CASE WHEN sentiment = 'POSITIVE' THEN 1 END) AS FLOAT) / COUNT(sentiment) AS positive_ratio " +
        "FROM mentions " +
        "WHERE managed_entity_id = :entityId " +
        "GROUP BY managed_entity_id", nativeQuery = true)
    Optional<SentimentStats> getSentimentStats(@Param("entityId") Long entityId);
}
