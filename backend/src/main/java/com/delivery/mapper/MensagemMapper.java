package com.delivery.mapper;

import com.delivery.dto.request.MensagemRequestDTO;
import com.delivery.dto.response.MensagemResponseDTO;
import com.delivery.model.Mensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemMapper {

    public Mensagem toEntity(MensagemRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        Mensagem mensagem = new Mensagem();
        mensagem.setContent(requestDTO.getContent());
        return mensagem;
    }

    public MensagemResponseDTO toResponseDTO(Mensagem mensagem) {
        if (mensagem == null) return null;
        MensagemResponseDTO responseDTO = new MensagemResponseDTO();
        responseDTO.setId(mensagem.getId());
        responseDTO.setSenderName(mensagem.getSenderName());
        responseDTO.setSenderRole(mensagem.getSenderRole());
        responseDTO.setContent(mensagem.getContent());
        responseDTO.setTimestamp(mensagem.getTimestamp());
        return responseDTO;
    }
}
