package com.delivery.service.impl;

import com.delivery.dto.request.LocalizacaoDTO;
import com.delivery.dto.request.MensagemRequestDTO;
import com.delivery.dto.response.MensagemResponseDTO;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.mapper.MensagemMapper;
import com.delivery.model.Mensagem;
import com.delivery.model.User;
import com.delivery.model.enums.OrderStatus;
import com.delivery.repository.MensagemRepository;
import com.delivery.repository.UserRepository;
import com.delivery.service.NotificacaoService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MensagemRepository mensagemRepository;
    private final UserRepository userRepository;
    private final MensagemMapper mensagemMapper;

    public NotificacaoServiceImpl(SimpMessagingTemplate messagingTemplate,
                                  MensagemRepository mensagemRepository,
                                  UserRepository userRepository,
                                  MensagemMapper mensagemMapper) {
        this.messagingTemplate = messagingTemplate;
        this.mensagemRepository = mensagemRepository;
        this.userRepository = userRepository;
        this.mensagemMapper = mensagemMapper;
    }

    @Override
    public void notifyStatusChange(Long orderId, OrderStatus status) {
        messagingTemplate.convertAndSend("/topico/pedidos/" + orderId + "/status", status.name());
    }

    @Override
    public void sendLiveLocation(Long orderId, LocalizacaoDTO location) {
        messagingTemplate.convertAndSend("/topico/pedidos/" + orderId + "/localizacao", location);
    }

    @Override
    public void notifyDeliveryArrival(Long orderId) {
        messagingTemplate.convertAndSend("/topico/pedidos/" + orderId + "/chegada", "O entregador chegou!");
    }

    @Override
    @Transactional
    public MensagemResponseDTO saveAndBroadcastMessage(Long orderId, String senderPhone, MensagemRequestDTO requestDTO) {
        User sender = userRepository.findByPhone(senderPhone)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário remetente não encontrado."));

        Mensagem mensagem = mensagemMapper.toEntity(requestDTO);
        mensagem.setOrderId(orderId);
        mensagem.setSenderPhone(senderPhone);
        mensagem.setSenderName(sender.getName() != null ? sender.getName() : "Usuário");
        mensagem.setSenderRole(sender.getRole().name());

        Mensagem savedMensagem = mensagemRepository.save(mensagem);

        MensagemResponseDTO responseDTO = mensagemMapper.toResponseDTO(savedMensagem);

        messagingTemplate.convertAndSend("/topico/pedidos/" + orderId + "/chat", responseDTO);

        return responseDTO;
    }
}

