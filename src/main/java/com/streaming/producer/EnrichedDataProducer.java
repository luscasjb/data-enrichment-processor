package com.streaming.producer;

import com.streaming.dto.EnrichedData;
import com.streaming.dto.kafka.Field;
import com.streaming.dto.kafka.KafkaPayload;
import com.streaming.dto.kafka.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EnrichedDataProducer {

    private static final String FINAL_TOPIC = "final_enriched_data";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EnrichedDataProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEnrichedData(EnrichedData finalData) {
        List<Field> fields = List.of(
                new Field("int64", false, "request_id"),
                new Field("int32", true, "person_id"),
                new Field("string", true, "person_name"),
                new Field("string", true, "status_description")
        );
        Schema schema = new Schema();
        schema.setFields(fields);
        schema.setName("com.streaming.EnrichedData");

        KafkaPayload<EnrichedData> kafkaPayload = new KafkaPayload<>(schema, finalData);

        kafkaTemplate.send(FINAL_TOPIC, finalData.getRequestId().toString(), kafkaPayload);
        log.info("Enriched message sent for request {}: {}", finalData.getRequestId(), finalData);
    }

    public void sendTombstone(Long requestId) {
        kafkaTemplate.send(FINAL_TOPIC, requestId.toString(), null);
        log.info("Tombstone message sent for request {}", requestId);
    }
}