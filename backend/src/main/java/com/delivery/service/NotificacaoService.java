package com.delivery.service;

import com.delivery.dto.request.LocalizacaoDTO;
import com.delivery.dto.request.MensagemRequestDTO;
import com.delivery.dto.response.MensagemResponseDTO;
import com.delivery.model.enums.OrderStatus;

public interface NotificacaoService {
    void notifyStatusChange(Long orderId, OrderStatus status);
    void sendLiveLocation(Long orderId, LocalizacaoDTO location);
    void notifyDeliveryArrival(Long orderId);
    MensagemResponseDTO saveAndBroadcastMessage(Long orderId, String senderPhone, MensagemRequestDTO requestDTO);
}
