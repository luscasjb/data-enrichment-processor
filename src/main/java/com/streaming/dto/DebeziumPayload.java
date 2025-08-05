package com.streaming.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebeziumPayload<T> {
    private String op;
    private T after;
    private T before;
}

