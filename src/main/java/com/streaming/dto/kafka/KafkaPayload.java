package com.streaming.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The main Kafka message envelope, containing the schema and the payload (the data).
 */
@Data
@AllArgsConstructor
public class KafkaPayload<T> {
    private Schema schema;
    private T payload;
}
