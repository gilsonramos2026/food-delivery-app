package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criação de um novo cupom de desconto pelo administrador")
public class CouponRequestDTO {

    @Schema(description = "Código textual do cupom", example = "QUERO10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O código do cupom é obrigatório")
    private String code;

    @Schema(description = "Valor fixo do desconto em reais", example = "10.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O valor do desconto é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor do desconto deve ser maior que zero")
    private BigDecimal discountValue;

    @Schema(description = "Valor mínimo do subtotal do pedido para habilitar o cupom", example = "30.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O valor mínimo do pedido é obrigatório")
    @DecimalMin(value = "0.0", message = "O valor mínimo não pode ser negativo")
    private BigDecimal minOrderValue;

    @Schema(description = "Data e hora limite de expiração do cupom", example = "2026-12-31T23:59:59", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A data de expiração é obrigatória")
    private LocalDateTime expiresAt;

    @Schema(description = "Quantidade máxima permitida de utilizações totais na plataforma", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O limite máximo de usos é obrigatório")
    @Positive(message = "O limite de usos deve ser maior que zero")
    private Integer maxUses;
}
