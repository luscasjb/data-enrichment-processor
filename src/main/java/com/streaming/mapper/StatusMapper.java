package com.streaming.mapper;

import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

    public String map(String statusAcronym) {
        return switch (statusAcronym) {
            case "PD" -> "PENDING";
            case "IP" -> "IN_PROGRESS";
            case "CP" -> "COMPLETED";
            case "CN" -> "CANCELLED";
            default -> "UNKNOWN";
        };
    }
}