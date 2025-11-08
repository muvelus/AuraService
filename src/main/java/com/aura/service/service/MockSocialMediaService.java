package com.aura.service.service;

import com.aura.service.enums.Platform;
import org.springframework.stereotype.Service;

@Service
public class MockSocialMediaService implements SocialMediaService {
    
    @Override
    public void fetchNewMentions() {
        System.out.println("Mock: Fetching new mentions from social media platforms...");
    }
    
    @Override
    public String postReply(Platform platform, String postId, String replyText) {
        String message = String.format(
                "Mock: Posted reply to %s post %s: %s",
                platform.name(),
                postId,
                replyText
        );
        System.out.println(message);
        return "Reply posted successfully (mock)";
    }
}
