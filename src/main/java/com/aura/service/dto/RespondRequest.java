package com.aura.service.dto;

import com.aura.service.enums.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondRequest {
    
    @NotNull(message = "Platform is required")
    private Platform platform;
    
    @NotBlank(message = "Post ID is required")
    private String postIdToReplyTo;
    
    @NotBlank(message = "Reply text is required")
    private String replyText;
}
