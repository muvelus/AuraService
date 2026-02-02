package com.aura.service.dto;

import com.aura.service.enums.Sentiment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateReplyRequest {
    
    @NotBlank(message = "Mention content is required")
    private String mentionContent;
    
    @NotNull(message = "Sentiment is required")
    private Sentiment sentiment;

    @NotBlank(message = "Movie title or celebrity name is required")
    private String managedEntityName;
}
