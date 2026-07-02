package com.stackwaze.swmo.controller;

import com.stackwaze.swmo.dto.OrderRequest;
import com.stackwaze.swmo.dto.OrderResponse;
import com.stackwaze.swmo.service.OrderService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(Authentication authentication, @RequestBody OrderRequest request) {
        String username = extractUsername(authentication);
        OrderResponse response = orderService.createOrder(username, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrders(Authentication authentication) {
        String username = extractUsername(authentication);
        return ResponseEntity.ok(orderService.listOrders(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(Authentication authentication, @PathVariable Long id) {
        String username = extractUsername(authentication);
        orderService.deleteOrder(username, id);
        return ResponseEntity.noContent().build();
    }

    private String extractUsername(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Map<String, Object> claims = jwtAuth.getToken().getClaims();
            Object preferredUsername = claims.get("preferred_username");
            if (preferredUsername instanceof String username) {
                return username;
            }
        }
        return authentication.getName();
    }
}
