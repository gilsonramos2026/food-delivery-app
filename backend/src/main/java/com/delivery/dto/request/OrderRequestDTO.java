package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Dados para abertura de um novo pedido")
public class OrderRequestDTO {

    @Schema(description = "Endereço textual de entrega do pedido", example = "Rua das Flores, 123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O endereço de entrega é obrigatório")
    private String address;

    @Schema(description = "Taxa calculada de entrega", example = "7.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A taxa de entrega é obrigatória")
    private BigDecimal deliveryFee;

    @Schema(description = "Data/Hora opcional de agendamento do pedido", example = "2026-09-05T19:30:00")
    private LocalDateTime scheduledAt;

    @Schema(description = "Lista contendo os produtos do pedido", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "O pedido precisa conter pelo menos um item")
    private List<OrderItemRequestDTO> items;
}
