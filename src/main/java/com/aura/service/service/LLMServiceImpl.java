package com.aura.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Map;

@Service
public class LLMServiceImpl implements LLMService {

    @Value("${llm.url}")
    private String llmUrl;

    @Value("${llm.prompt.generate.reply}")
    private String llmGenerateReplyPrompt;

    @Value("${llm.prompt.generate.crisis.plan}")
    private String llmGenerateCrisisPlanPrompt;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public LLMServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateReply(String prompt) {
        return callLlm(prompt);
    }

    @Override
    public String generateCrisisPlan(String prompt) {
        return callLlm(prompt);
    }

    private String callLlm(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, String> requestBody = Collections.singletonMap("prompt", prompt);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(llmUrl, entity, String.class);
            try {
                JsonNode root = objectMapper.readTree(response);
                JsonNode replyNode = root.path("reply");
                if (replyNode.isMissingNode()) {
                    replyNode = root.path("response");
                }
                if (replyNode.isMissingNode()) {
                    replyNode = root.path("generated_text");
                }
                return replyNode.isMissingNode() ? response : replyNode.asText();
            } catch (JsonProcessingException e) {
                // Not a JSON response, return as is.
                return response;
            }
        } catch (Exception e) {
            System.err.println("Error calling LLM service: " + e.getMessage());
            return "Error generating reply from LLM.";
        }
    }
}
