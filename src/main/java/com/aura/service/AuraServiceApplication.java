package com.aura.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuraServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuraServiceApplication.class, args);
		
		System.out.println("AuraService is running!");
		System.out.println("Available endpoints:");
		System.out.println("  GET  /api/dashboard/{entityType}/{entityId}/stats - Get entity statistics");
		System.out.println("  GET  /api/dashboard/{entityType}/{entityId}/competitor-snapshot - Get competitor snapshot");
		System.out.println("  GET  /api/dashboard/{entityType}/{entityId}/sentiment-over-time - Get sentiment over time");
		System.out.println("  GET  /api/dashboard/{entityType}/{entityId}/platform-mentions - Get platform breakdown with sentiment");
		System.out.println("  GET  /api/dashboard/{entityType}/{entityId}/mentions - Get mentions with optional filters");
	}

}
