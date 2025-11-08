package com.aura.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuraServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AuraServiceApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("Aura Service Application Started Successfully!");
        System.out.println("===========================================");
        System.out.println("API Base URL: http://localhost:8080/api");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
        System.out.println("\nDefault User Credentials:");
        System.out.println("  Username: user");
        System.out.println("  Password: password");
        System.out.println("\nAPI Endpoints:");
        System.out.println("  POST /api/auth/register - Register new user");
        System.out.println("  POST /api/auth/login - Login and get JWT token");
        System.out.println("  POST /api/entities - Create managed entity");
        System.out.println("  GET  /api/entities - List all entities");
        System.out.println("  GET  /api/entities/{id} - Get entity details");
        System.out.println("  PUT  /api/entities/{id}/competitors - Update competitors");
        System.out.println("  GET  /api/dashboard/{entityId}/stats - Get entity stats");
        System.out.println("  GET  /api/dashboard/{entityId}/competitor-snapshot - Get competitor comparison");
        System.out.println("  GET  /api/dashboard/{entityId}/sentiment-over-time - Get sentiment time series");
        System.out.println("  GET  /api/dashboard/{entityId}/platform-mentions - Get platform breakdown");
        System.out.println("  GET  /api/dashboard/{entityId}/mentions - Get filtered mentions");
        System.out.println("  POST /api/interact/generate-reply - Generate AI reply");
        System.out.println("  POST /api/interact/respond - Post reply to platform");
        System.out.println("  POST /api/crisis/generate-plan - Generate crisis plan");
        System.out.println("  GET  /api/analytics/box-office-prediction - Get box office prediction");
        System.out.println("  GET  /api/analytics/trending-genre - Get trending genre");
        System.out.println("  GET  /api/analytics/hit-genre-prediction - Get hit genre prediction");
        System.out.println("  GET  /api/analytics/best-genre - Get best genre");
        System.out.println("  GET  /api/analytics/top-box-office - Get top box office");
        System.out.println("===========================================\n");
    }
}
