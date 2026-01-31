package com.aura.service.dto;

import com.aura.service.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityDetailResponse {
    private Long id;
    private String name;
    private EntityType type;
    private String director;
    private List<String> actors = new ArrayList<>();
    private List<String> keywords = new ArrayList<>();
    private List<EntityBasicInfo> competitors = new ArrayList<>();
}
