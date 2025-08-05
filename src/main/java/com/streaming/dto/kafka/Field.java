package com.streaming.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a single field within the schema.
 */
@Data
@AllArgsConstructor
public class Field {
    private String type;
    private boolean optional;
    private String field;
}