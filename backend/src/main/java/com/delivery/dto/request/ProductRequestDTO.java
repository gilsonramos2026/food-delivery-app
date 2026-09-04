package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "Dados necessários para criar ou atualizar um produto")
public class ProductRequestDTO {

    @Schema(description = "Nome do produto", example = "Pizza Calabresa", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome do produto é obrigatório")
    private String name;

    @Schema(description = "Descrição detalhada dos ingredientes ou do item", example = "Molho de tomate, mozarela, calabresa e cebola")
    private String description;

    @Schema(description = "Preço de venda do produto", example = "45.90", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal price;

    @Schema(description = "Preço de custo do produto para o dashboard do administrador", example = "18.50")
    private BigDecimal costPrice;

    @Schema(description = "ID da categoria à qual o produto pertence", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoryId;
}
