package com.aura.service.service;

public interface LLMService {
    String generateReply(String prompt);

    String generateCrisisPlan(String prompt);
}
