package com.ms.orders.service;

import java.util.List;
import java.util.UUID;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.azure.cosmos.models.PartitionKey;

import com.ms.orders.domain.Order;
import com.ms.orders.dto.OrderDTO;
import com.ms.orders.mapper.OrderMapper;
import com.ms.orders.repository.OrderRepository;
import com.ms.orders.repository.OutboxEventRepository;
import com.ms.orders.domain.EventType;
import com.ms.orders.domain.OutboxEvent;

@Service
public class OrdersService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OutboxEventRepository outboxEventRepository;
    private final tools.jackson.databind.ObjectMapper objectMapper;

    public OrdersService(OrderRepository orderRepository, OrderMapper orderMapper,
            OutboxEventRepository outboxEventRepository, tools.jackson.databind.ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public String createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setId(UUID.randomUUID().toString());
        Date now = new Date();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        Order savedOrder = orderRepository.save(order);

        try {
            String jsonPayload = objectMapper.writeValueAsString(savedOrder);
            OutboxEvent outboxEvent = new OutboxEvent(jsonPayload, EventType.ORDER_CREATED);
            outboxEvent.setId(UUID.randomUUID().toString());
            outboxEvent.setCreatedAt(now);
            outboxEvent.setUpdatedAt(now);
            outboxEventRepository.save(outboxEvent);
        } catch (tools.jackson.core.JacksonException e) {
            throw new RuntimeException("Error serializing order payload to JSON", e);
        }

        return savedOrder.getId();
    }

    public OrderDTO getOrderById(String id, String customerId) {
        return orderRepository.findById(id, new PartitionKey(customerId))
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public List<OrderDTO> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(orderMapper::toDTO)
                .toList();
    }
}
