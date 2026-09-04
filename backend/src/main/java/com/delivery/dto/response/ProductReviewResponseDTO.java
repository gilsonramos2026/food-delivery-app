package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno da avaliação de um produto específico")
public class ProductReviewResponseDTO {
    private Long id;
    private String userName;
    private Long productId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
