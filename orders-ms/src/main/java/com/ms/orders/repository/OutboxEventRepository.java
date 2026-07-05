package com.ms.orders.repository;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.ms.orders.domain.OutboxEvent;

@Repository
public interface OutboxEventRepository extends CosmosRepository<OutboxEvent, String> {

}
