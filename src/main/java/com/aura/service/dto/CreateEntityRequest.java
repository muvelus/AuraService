package com.aura.service.dto;

import com.aura.service.enums.EntityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEntityRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Type is required")
    private EntityType type;
    
    private String director;
    
    private List<String> actors = new ArrayList<>();
}
