package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
@Schema(description = "Estrutura detalhada de resposta para erros de validação de campos (Bean Validation)")
public class ValidationErrorDTO extends ErrorResponseDTO {

    @Schema(
            description = "Mapeamento dos campos que falharam na validação e seus respectivos motivos em português",
            example = "{\"price\": \"O preço deve ser maior que zero\", \"name\": \"O nome do produto é obrigatório\"}"
    )
    private final Map<String, String> fields;
}
