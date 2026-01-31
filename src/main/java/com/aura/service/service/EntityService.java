package com.aura.service.service;

import com.aura.service.dto.CreateEntityRequest;
import com.aura.service.dto.EntityBasicInfo;
import com.aura.service.dto.EntityDetailResponse;
import com.aura.service.dto.UpdateCompetitorsRequest;
import com.aura.service.dto.UpdateKeywordsRequest;
import com.aura.service.entity.ManagedEntity;
import com.aura.service.repository.ManagedEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntityService {
    
    private final ManagedEntityRepository entityRepository;
    
    @Transactional
    public EntityDetailResponse createEntity(CreateEntityRequest request) {
        ManagedEntity entity = new ManagedEntity();
        entity.setName(request.getName());
        entity.setType(request.getType());
        entity.setDirector(request.getDirector());
        entity.setActors(request.getActors());
        entity.setKeywords(request.getKeywords());
        
        entity = entityRepository.save(entity);
        
        return mapToDetailResponse(entity);
    }
    
    public List<EntityBasicInfo> getAllEntities() {
        return entityRepository.findAll().stream()
                .map(this::mapToBasicInfo)
                .collect(Collectors.toList());
    }
    
    public EntityDetailResponse getEntityById(Long id) {
        ManagedEntity entity = entityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
        return mapToDetailResponse(entity);
    }
    
    @Transactional
    public EntityDetailResponse updateCompetitors(Long id, UpdateCompetitorsRequest request) {
        ManagedEntity entity = entityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
        
        List<ManagedEntity> competitors = entityRepository.findAllById(request.getCompetitorIds());
        entity.setCompetitors(competitors);
        
        entity = entityRepository.save(entity);
        
        return mapToDetailResponse(entity);
    }

    @Transactional
    public EntityDetailResponse updateKeywords(Long id, UpdateKeywordsRequest request) {
        ManagedEntity entity = entityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
        
        entity.setKeywords(request.getKeywords());
        
        entity = entityRepository.save(entity);
        
        return mapToDetailResponse(entity);
    }
    
    private EntityBasicInfo mapToBasicInfo(ManagedEntity entity) {
        return new EntityBasicInfo(entity.getId(), entity.getName(), entity.getType());
    }
    
    private EntityDetailResponse mapToDetailResponse(ManagedEntity entity) {
        EntityDetailResponse response = new EntityDetailResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setType(entity.getType());
        response.setDirector(entity.getDirector());
        response.setActors(entity.getActors());
        response.setKeywords(entity.getKeywords());
        response.setCompetitors(
                entity.getCompetitors().stream()
                        .map(this::mapToBasicInfo)
                        .collect(Collectors.toList())
        );
        return response;
    }
}
