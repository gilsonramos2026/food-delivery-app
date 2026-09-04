package com.delivery.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class ValidationErrorDTO extends ErrorResponseDTO {
    private final Map<String, String> fields;
}
