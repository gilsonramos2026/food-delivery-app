package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Métricas de consolidação de faturamento e rentabilidade exclusivas do administrador")
public class DashboardResponseDTO {

    @Schema(description = "Soma bruta de todo o dinheiro recebido por pedidos concluídos", example = "15450.90")
    private BigDecimal revenue;

    @Schema(description = "Lucro real obtido calculando (Preço de Venda - Preço de Custo) dos produtos vendidos", example = "7820.40")
    private BigDecimal profit;

    @Schema(description = "Lista contendo os 5 produtos mais vendidos do mês atual")
    private List<TopProductDTO> topProducts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopProductDTO {
        @Schema(description = "Nome do produto comercializado", example = "Pizza Calabresa")
        private String name;

        @Schema(description = "Quantidade total de unidades vendidas", example = "342")
        private Long salesCount;
    }
}
