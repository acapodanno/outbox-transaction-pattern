package com.ms.orders.repository;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.ms.orders.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends CosmosRepository<Order, String> {
    List<Order> findByCustomerId(String customerId);
}
