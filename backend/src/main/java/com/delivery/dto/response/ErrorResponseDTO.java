package com.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {
    private final Instant timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;
}

