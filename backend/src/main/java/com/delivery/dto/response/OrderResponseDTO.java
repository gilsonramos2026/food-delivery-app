package com.delivery.dto.response;

import com.delivery.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Estrutura de retorno dos dados de um pedido")
public class OrderResponseDTO {
    private Long id;
    private Long clientId;
    private OrderStatus status;
    private String address;
    private BigDecimal total;
    private BigDecimal deliveryFee;
    private LocalDateTime scheduledAt;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> items;

    @Data
    public static class OrderItemResponseDTO {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
    }
}
