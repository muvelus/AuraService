package com.aura.service.repository;

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

@Repository
public interface MentionRepository extends JpaRepository<Mention, Long> {
    
    List<Mention> findByManagedEntityId(Long entityId);
    
    long countByManagedEntityId(Long entityId);
    
    long countByManagedEntityIdAndSentiment(Long entityId, Sentiment sentiment);
    
    @Query("SELECT m.platform, COUNT(m) FROM Mention m WHERE m.managedEntity.id = :entityId GROUP BY m.platform")
    List<Object[]> countByPlatformForEntity(@Param("entityId") Long entityId);
    
    @Query(value = "SELECT * FROM mentions m WHERE m.managed_entity_id = :entityId " +
           "AND (:platform IS NULL OR m.platform = :platform) " +
           "AND (:country IS NULL OR m.location_country = :country) " +
           "AND (:city IS NULL OR m.location_city = :city) " +
           "AND (:age IS NULL OR m.author_age = :age) " +
           "AND (CAST(:startDate as text) IS NULL OR m.post_date >= :startDate) " +
           "AND (CAST(:endDate as text) IS NULL OR m.post_date <= :endDate)",
           nativeQuery = true)
    Page<Mention> findFilteredMentions(
        @Param("entityId") Long entityId,
        @Param("platform") Platform platform,
        @Param("country") String country,
        @Param("city") String city,
        @Param("age") Integer age,
        @Param("startDate") Instant startDate,
        @Param("endDate") Instant endDate,
        Pageable pageable
    );
    
    @Query("SELECT m FROM Mention m WHERE m.managedEntity.id IN :entityIds " +
           "AND m.postDate >= :startDate AND m.postDate <= :endDate")
    List<Mention> findByEntityIdsAndDateRange(
        @Param("entityIds") List<Long> entityIds,
        @Param("startDate") Instant startDate,
        @Param("endDate") Instant endDate
    );
}
