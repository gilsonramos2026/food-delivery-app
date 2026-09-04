package com.delivery.mapper;

import com.delivery.dto.response.OrderResponseDTO;
import com.delivery.model.Order;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDTO toResponseDTO(Order order) {
        if (order == null) return null;
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setClientId(order.getClient().getId());
        dto.setStatus(order.getStatus());
        dto.setAddress(order.getAddress());
        dto.setTotal(order.getTotal());
        dto.setDeliveryFee(order.getDeliveryFee());
        dto.setScheduledAt(order.getScheduledAt());
        dto.setCancellationReason(order.getCancellationReason());
        dto.setCreatedAt(order.getCreatedAt());

        dto.setItems(order.getItems().stream().map(item -> {
            OrderResponseDTO.OrderItemResponseDTO itemDto = new OrderResponseDTO.OrderItemResponseDTO();
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            return itemDto;
        }).collect(Collectors.toList()));

        return dto;
    }
}
