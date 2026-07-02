package com.stackwaze.swmo.service;

import com.stackwaze.swmo.domain.OrderEntity;
import com.stackwaze.swmo.dto.OrderRequest;
import com.stackwaze.swmo.dto.OrderResponse;
import com.stackwaze.swmo.repository.OrderRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProducerTemplate producerTemplate;

    public OrderService(OrderRepository orderRepository, ProducerTemplate producerTemplate) {
        this.orderRepository = orderRepository;
        this.producerTemplate = producerTemplate;
    }

    public OrderResponse createOrder(String username, OrderRequest request) {
        Map<String, Integer> items = request.getItems() == null ? Map.of() : request.getItems().entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (items.isEmpty()) {
            throw new IllegalArgumentException("La commande doit contenir au moins un produit.");
        }

        OrderEntity order = new OrderEntity(username, items);
        order = orderRepository.save(order);
        producerTemplate.sendBody("direct:order-logger", order);

        return new OrderResponse(order.getId(), order.getUsername(), order.getCreatedAt(), order.getItems());
    }

    public List<OrderResponse> listOrders(String username) {
        return orderRepository.findByUsername(username).stream()
                .sorted((left, right) -> right.getCreatedAt().compareTo(left.getCreatedAt()))
                .map(order -> new OrderResponse(order.getId(), order.getUsername(), order.getCreatedAt(), order.getItems()))
                .collect(Collectors.toList());
    }

    public void deleteOrder(String username, Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable."));

        if (!order.getUsername().equals(username)) {
            throw new IllegalArgumentException("Accès refusé.");
        }

        orderRepository.delete(order);
    }
}
