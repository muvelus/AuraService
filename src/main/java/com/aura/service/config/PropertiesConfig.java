package com.aura.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:secrets.properties")
public class PropertiesConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
