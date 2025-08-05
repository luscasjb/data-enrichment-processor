package com.streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrichedData {
    @JsonProperty("request_id")
    private Long requestId;

    @JsonProperty("person_id")
    private Integer personId;

    @JsonProperty("person_name")
    private String personName;

    @JsonProperty("status_description")
    private String statusDescription;
}
