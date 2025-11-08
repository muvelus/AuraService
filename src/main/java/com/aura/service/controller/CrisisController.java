package com.aura.service.controller;

import com.aura.service.dto.GenerateCrisisPlanRequest;
import com.aura.service.dto.GenerateCrisisPlanResponse;
import com.aura.service.entity.ManagedEntity;
import com.aura.service.repository.ManagedEntityRepository;
import com.aura.service.service.LLMService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crisis")
@RequiredArgsConstructor
public class CrisisController {
    
    private final LLMService llmService;
    private final ManagedEntityRepository entityRepository;
    
    @PostMapping("/generate-plan")
    public ResponseEntity<GenerateCrisisPlanResponse> generateCrisisPlan(
            @Valid @RequestBody GenerateCrisisPlanRequest request
    ) {
        ManagedEntity entity = entityRepository.findById(request.getEntityId())
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + request.getEntityId()));
        
        String prompt = String.format(
                "Generate a detailed crisis management plan for %s (%s) regarding the following crisis: %s",
                entity.getName(),
                entity.getType().name(),
                request.getCrisisDescription()
        );
        
        String generatedPlan = llmService.generateCrisisPlan(prompt);
        return ResponseEntity.ok(new GenerateCrisisPlanResponse(generatedPlan));
    }
}
