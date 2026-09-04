package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para cadastro de banners promocionais pelo administrador")
public class BannerRequestDTO {

    @Schema(description = "Título de identificação do banner", example = "Combo de Sexta", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O título do banner é obrigatório")
    private String title;

    @Schema(description = "Ordem numérica de prioridade de exibição na Home", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A ordem de exibição é obrigatória")
    private Integer displayOrder;
}
