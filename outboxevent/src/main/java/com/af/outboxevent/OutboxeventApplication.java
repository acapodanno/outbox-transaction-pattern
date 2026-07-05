package com.af.outboxevent;

import com.af.outboxevent.model.OutboxEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
public class OutboxeventApplication {

	private static final Logger log = LoggerFactory.getLogger(OutboxeventApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OutboxeventApplication.class, args);
	}

	@Bean
	public Consumer<List<OutboxEvent>> outboxEventTrigger() {
		return events -> {
			log.info("Processing {} outbox events in Spring Cloud Function bean", events.size());
			for (OutboxEvent event : events) {
				log.info("Received event: ID={}, EventType={}, Payload={}", 
						event.id(), event.eventType(), event.payload());
			}
		};
	}

}
