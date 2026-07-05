package com.af.outboxevent.model;

import java.util.Date;

public record OutboxEvent(
    String id,
    String payload,
    EventType eventType,
    Date createdAt,
    Date updatedAt
) {}
