package com.aura.service.controller;

import com.aura.service.dto.GenerateReplyRequest;
import com.aura.service.dto.GenerateReplyResponse;
import com.aura.service.dto.RespondRequest;
import com.aura.service.service.LLMService;
import com.aura.service.service.SocialMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interact")
@RequiredArgsConstructor
public class InteractionController {
    
    private final LLMService llmService;
    private final SocialMediaService socialMediaService;
    
    @PostMapping("/generate-reply")
    public ResponseEntity<GenerateReplyResponse> generateReply(@Valid @RequestBody GenerateReplyRequest request) {
        String prompt = String.format(
                "Generate a professional reply to the following %s mention: %s",
                request.getSentiment().name().toLowerCase(),
                request.getMentionContent()
        );
        
        String generatedReply = llmService.generateReply(prompt);
        return ResponseEntity.ok(new GenerateReplyResponse(generatedReply));
    }
    
    @PostMapping("/respond")
    public ResponseEntity<String> respond(@Valid @RequestBody RespondRequest request) {
        String result = socialMediaService.postReply(
                request.getPlatform(),
                request.getPostIdToReplyTo(),
                request.getReplyText()
        );
        return ResponseEntity.ok(result);
    }
}
