package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para envio da nota e comentário geral do pedido feito pelo cliente")
public class ReviewRequestDTO {

    @Schema(description = "ID do pedido que está sendo avaliado", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID do pedido é obrigatório")
    private Long orderId;

    @Schema(description = "Nota geral para a experiência/entrega (1 a 5)", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota mínima é 1")
    @Max(value = 5, message = "A nota máxima é 5")
    private Integer rating;

    @Schema(description = "Comentário textual opcional sobre a entrega ou o serviço", example = "Chegou super rápido e quentinho!")
    private String comment;
}
