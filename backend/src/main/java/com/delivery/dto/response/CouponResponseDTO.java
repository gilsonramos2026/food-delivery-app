package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno do cupom consultado ou salvo")
public class CouponResponseDTO {
    private Long id;
    private String code;
    private BigDecimal discountValue;
    private BigDecimal minOrderValue;
    private Boolean active;
    private LocalDateTime expiresAt;
    private Integer maxUses;
    private Integer currentUses;
}
