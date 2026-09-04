package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Estrutura padrão de resposta para erros gerados pela API")
public class ErrorResponseDTO {

    @Schema(description = "Data e hora exata em que o erro ocorreu", example = "2026-09-04T16:54:39Z")
    private final Instant timestamp;

    @Schema(description = "Código de status HTTP do erro", example = "400")
    private final Integer status;

    @Schema(description = "Título descritivo do tipo do erro HTTP", example = "Violação de regra de negócio")
    private final String error;

    @Schema(description = "Mensagem detalhada em português explicando o motivo do erro", example = "Código de verificação incorreto.")
    private final String message;

    @Schema(description = "Caminho da URL (endpoint) que originou a requisição com erro", example = "/auth/verificar")
    private final String path;
}
