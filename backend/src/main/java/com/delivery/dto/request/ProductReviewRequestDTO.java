package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para envio da nota individual por produto")
public class ProductReviewRequestDTO {

    @Schema(description = "ID do produto avaliado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID do produto é obrigatório")
    private Long productId;

    @Schema(description = "Nota dada especificamente para a qualidade do item (1 a 5)", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A nota do produto é obrigatória")
    @Min(value = 1, message = "A nota mínima para o produto é 1")
    @Max(value = 5, message = "A nota máxima para o produto é 5")
    private Integer rating;

    @Schema(description = "Comentário opcional sobre o sabor ou porção do produto", example = "A massa estava excelente.")
    private String comment;
}
