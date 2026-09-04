package com.delivery.service;

import com.delivery.dto.request.OrderRequestDTO;
import com.delivery.dto.response.OrderResponseDTO;
import com.delivery.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO requestDTO, String clientPhone);
    OrderResponseDTO advanceStatus(Long orderId, OrderStatus nextStatus);
    OrderResponseDTO cancelOrder(Long orderId, String clientPhone, String reason, boolean isAdmin);
    List<OrderResponseDTO> findAll();
    List<OrderResponseDTO> findByClient(String clientPhone);
}

