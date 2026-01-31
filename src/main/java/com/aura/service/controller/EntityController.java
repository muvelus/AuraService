package com.aura.service.controller;

import com.aura.service.dto.CreateEntityRequest;
import com.aura.service.dto.EntityBasicInfo;
import com.aura.service.dto.EntityDetailResponse;
import com.aura.service.dto.UpdateCompetitorsRequest;
import com.aura.service.dto.UpdateKeywordsRequest;
import com.aura.service.service.EntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entities")
@RequiredArgsConstructor
public class EntityController {
    
    private final EntityService entityService;
    
    @PostMapping
    public ResponseEntity<EntityDetailResponse> createEntity(@Valid @RequestBody CreateEntityRequest request) {
        EntityDetailResponse response = entityService.createEntity(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<EntityBasicInfo>> getAllEntities() {
        List<EntityBasicInfo> entities = entityService.getAllEntities();
        return ResponseEntity.ok(entities);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityDetailResponse> getEntityById(@PathVariable Long id) {
        EntityDetailResponse response = entityService.getEntityById(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/competitors")
    public ResponseEntity<EntityDetailResponse> updateCompetitors(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCompetitorsRequest request
    ) {
        EntityDetailResponse response = entityService.updateCompetitors(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/keywords")
    public ResponseEntity<EntityDetailResponse> updateKeywords(
            @PathVariable Long id,
            @Valid @RequestBody UpdateKeywordsRequest request
    ) {
        EntityDetailResponse response = entityService.updateKeywords(id, request);
        return ResponseEntity.ok(response);
    }
}
