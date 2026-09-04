package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Dados necessários para criar ou atualizar uma categoria")
public class CategoryRequestDTO {

    @Schema(description = "Nome exclusivo da categoria do cardápio", example = "Pizzas Doces", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome da categoria é obrigatório")
    private String name;

    @Schema(description = "Ordem numérica de exibição no menu do aplicativo", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A ordem de exibição é obrigatória")
    private Integer displayOrder;
}
