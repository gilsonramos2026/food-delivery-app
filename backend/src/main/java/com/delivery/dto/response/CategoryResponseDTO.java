package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Dados de retorno da categoria")
public class CategoryResponseDTO {

    @Schema(description = "ID único da categoria gerado no banco de dados", example = "1")
    private Long id;

    @Schema(description = "Nome da categoria", example = "Pizzas Doces")
    private String name;

    @Schema(description = "Ordem de exibição no cardápio", example = "1")
    private Integer displayOrder;
}
