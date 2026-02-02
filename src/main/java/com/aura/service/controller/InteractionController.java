package com.aura.service.controller;

import com.aura.service.dto.GenerateReplyRequest;
import com.aura.service.dto.GenerateReplyResponse;
import com.aura.service.dto.RespondRequest;
import com.aura.service.service.LLMService;
import com.aura.service.service.SocialMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interact")
@RequiredArgsConstructor
public class InteractionController {

    private final LLMService llmService;
    private final SocialMediaService socialMediaService;

    @Value("${llm.prompt.generate.reply}")
    private String generateReplyPrompt;

    @PostMapping("/generate-reply")
    public ResponseEntity<GenerateReplyResponse> generateReply(@Valid @RequestBody GenerateReplyRequest request) {
        String prompt = generateReplyPrompt
                .replace("[Managed Entity]", request.getMovieOrCelebrityName())
                .replace("[Paste the user's post here]", request.getMentionContent())
                .replace("[Positive / Negative / Neutral]", request.getSentiment().name());

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
