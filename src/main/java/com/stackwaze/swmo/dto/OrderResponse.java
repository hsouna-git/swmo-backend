package com.stackwaze.swmo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;

    private Map<String, Integer> items;
}
