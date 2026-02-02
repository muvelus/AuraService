package com.aura.service.entity;

import com.aura.service.enums.Platform;
import com.aura.service.enums.Sentiment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "mentions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mention {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "managed_entity_id", nullable = false)
    private ManagedEntity managedEntity;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;
    
    @Column(unique = true, nullable = false)
    private String postId;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column
    private String author;
    
    @Column(nullable = false)
    private Instant postDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sentiment sentiment;
    
    @Column
    private String permalink;

    @Column(name = "sentiment_score")
    private Short sentimentScore;
}
