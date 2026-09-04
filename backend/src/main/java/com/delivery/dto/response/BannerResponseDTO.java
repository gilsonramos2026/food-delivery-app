package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno do banner ativo para exibição no aplicativo")
public class BannerResponseDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private Integer displayOrder;
    private Boolean active;
}
