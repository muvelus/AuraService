package com.aura.service.dto;

import com.aura.service.enums.Platform;
import com.aura.service.enums.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentionResponse {
    private Long id;
    private Long managedEntityId;
    private Platform platform;
    private String postId;
    private String content;
    private String author;
    private Integer authorAge;
    private String locationCountry;
    private String locationCity;
    private Instant postDate;
    private Sentiment sentiment;
}
