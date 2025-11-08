package com.aura.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCrisisPlanRequest {
    
    @NotNull(message = "Entity ID is required")
    private Long entityId;
    
    @NotBlank(message = "Crisis description is required")
    private String crisisDescription;
}
