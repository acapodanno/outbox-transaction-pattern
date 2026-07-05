package com.ms.orders.domain;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import com.azure.spring.data.cosmos.core.mapping.Container;

@Container(containerName = "outboxEvents")
public class OutboxEvent {
    @Id
    private String id;

    private String payload;
    private EventType eventType;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OutboxEvent() {
    }

    public OutboxEvent(String id, String payload, EventType eventType, Date createdAt, Date updatedAt) {
        this.id = id;
        this.payload = payload;
        this.eventType = eventType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OutboxEvent(String payload, EventType eventType) {
        this.payload = payload;
        this.eventType = eventType;
    }
}
