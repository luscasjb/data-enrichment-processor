package com.streaming.listener;

import com.streaming.dto.DebeziumPayload;
import com.streaming.dto.Movement;
import com.streaming.service.DataEnrichmentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DebeziumEventListener {

    private final ObjectMapper objectMapper;
    private final DataEnrichmentService dataEnrichmentService;

    public DebeziumEventListener(ObjectMapper objectMapper, DataEnrichmentService dataEnrichmentService) {
        this.objectMapper = objectMapper;
        this.dataEnrichmentService = dataEnrichmentService;
    }

    @KafkaListener(topics = "mysql.local.listener", groupId = "data-enricher-group")
    public void consume(String message) {
        try {
            DebeziumPayload<Movement> payload = objectMapper.readValue(message, new TypeReference<>() {});
            Movement movement = payload.getAfter();

            if (movement == null) {
                log.info("Delete event received on movements table. Ignoring.");
                return;
            }

            dataEnrichmentService.processMovement(movement);

        } catch (Exception e) {
            log.error("Fatal error processing Kafka message: " + message, e);
            // TO DO: implementing a retry policy or sending to a Dead Letter Queue (DLQ)
        }
    }
}