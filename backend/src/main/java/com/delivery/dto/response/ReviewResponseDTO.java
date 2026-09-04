package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno da avaliação geral de um pedido")
public class ReviewResponseDTO {
    private Long id;
    private Long orderId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
