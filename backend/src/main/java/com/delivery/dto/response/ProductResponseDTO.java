package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "Dados de retorno do produto")
public class ProductResponseDTO {

    @Schema(description = "ID único do produto gerado no banco de dados", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Pizza Calabresa")
    private String name;

    @Schema(description = "Descrição dos ingredientes do item", example = "Molho de tomate, mozarela, calabresa e cebola")
    private String description;

    @Schema(description = "Preço de venda praticado", example = "45.90")
    private BigDecimal price;

    @Schema(description = "Preço de custo para controle interno de dashboard", example = "18.50")
    private BigDecimal costPrice;

    @Schema(description = "Caminho ou URL da imagem salva no servidor", example = "/uploads/a1b2c3d4-e5f6.jpg")
    private String imageUrl;

    @Schema(description = "Dados detalhados da categoria à qual este produto pertence")
    private CategoryResponseDTO category;
}
