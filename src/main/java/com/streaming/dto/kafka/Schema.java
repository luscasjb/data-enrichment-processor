package com.streaming.dto.kafka;

import lombok.Data;

import java.util.List;

@Data
public class Schema {
    private String type = "struct";
    private List<Field> fields;
    private boolean optional = false;
    private String name;
}

