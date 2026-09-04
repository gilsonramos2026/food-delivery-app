package com.delivery.controller;

import com.delivery.dto.response.MensagemResponseDTO;
import com.delivery.repository.MensagemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/pedidos/{orderId}/mensagens")
public class MensagemController {

    private final MensagemRepository mensagemRepository;

    public MensagemController(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENT', 'COURIER', 'ADMIN')")
    public ResponseEntity<List<MensagemResponseDTO>> getChatHistory(@PathVariable Long orderId) {
        List<MensagemResponseDTO> history = mensagemRepository.findByOrderIdOrderByTimestampAsc(orderId).stream()
                .map(m -> {
                    MensagemResponseDTO dto = new MensagemResponseDTO();
                    dto.setId(m.getId());
                    dto.setSenderName(m.getSenderName());
                    dto.setSenderRole(m.getSenderRole());
                    dto.setContent(m.getContent());
                    dto.setTimestamp(m.getTimestamp());
                    return dto;
                }).toList();
        return ResponseEntity.ok(history);
    }
}

