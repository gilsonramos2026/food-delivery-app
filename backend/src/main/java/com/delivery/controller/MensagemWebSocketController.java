package com.delivery.controller;

import com.delivery.dto.request.MensagemRequestDTO;
import com.delivery.service.NotificacaoService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class MensagemWebSocketController { // Nome corrigido para bater com o arquivo físico

    private final NotificacaoService notificacaoService;

    public MensagemWebSocketController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @MessageMapping("/orders/{orderId}/chat.sendMessage")
    public void sendMessage(@DestinationVariable Long orderId, MensagemRequestDTO requestDTO, SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = headerAccessor.getUser();
        String userPhone = principal != null ? principal.getName() : "00000000000";

        notificacaoService.saveAndBroadcastMessage(orderId, userPhone, requestDTO);
    }
}
