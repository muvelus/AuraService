package com.aura.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityBasicInfo {
    private Long id;
    private String name;
    private String type;
    private String director;
    private LocalDate releaseDate;

    public EntityBasicInfo(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
