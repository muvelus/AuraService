package com.aura.service.service;

import com.aura.service.enums.Platform;

public interface SocialMediaService {
    void fetchNewMentions();
    String postReply(Platform platform, String postId, String replyText);
}
