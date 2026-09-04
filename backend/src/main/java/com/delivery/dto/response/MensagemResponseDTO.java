package com.delivery.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MensagemResponseDTO {
    private Long id;
    private String senderName;
    private String senderRole;
    private String content;
    private LocalDateTime timestamp;
}
