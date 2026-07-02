package com.stackwaze.swmo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthCheckService {

    private final RestTemplate restTemplate;
    private final List<String> healthCheckUrls;

    public HealthCheckService(
            RestTemplate restTemplate,
            @Value("${app.healthcheck.url:https://swmo-backend-production.up.railway.app/api/health}") String backendHealthCheckUrl,
            @Value("${app.healthcheck.keycloak-url:https://keycloak-production-5d0e.up.railway.app/}") String keycloakHealthCheckUrl) {
        this.restTemplate = restTemplate;
        this.healthCheckUrls = List.of(backendHealthCheckUrl, keycloakHealthCheckUrl);
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 5000)
    public void selfHealthCheck() {
        for (String healthCheckUrl : healthCheckUrls) {
            try {
                String response = restTemplate.getForObject(healthCheckUrl, String.class);
                System.out.println("[HealthCheck] Ping successful for " + healthCheckUrl + ": " + response);
            } catch (RestClientException e) {
                System.err.println("[HealthCheck] Ping failed for " + healthCheckUrl + ": " + e.getMessage());
            }
        }
    }
}
