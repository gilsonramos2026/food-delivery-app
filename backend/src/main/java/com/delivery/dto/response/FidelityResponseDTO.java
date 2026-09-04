package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno do status do cartão fidelidade do cliente logado")
public class FidelityResponseDTO {

    @Schema(description = "Quantidade de pedidos ENTREGUES no ciclo atual", example = "7")
    private Integer orderCount;

    @Schema(description = "Informa se o cliente atingiu a marca de 10 pedidos e possui um brinde pendente", example = "false")
    private Boolean rewardAvailable;
}
