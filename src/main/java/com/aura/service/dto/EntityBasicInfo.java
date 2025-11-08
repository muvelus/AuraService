package com.aura.service.dto;

import com.aura.service.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityBasicInfo {
    private Long id;
    private String name;
    private EntityType type;
}
