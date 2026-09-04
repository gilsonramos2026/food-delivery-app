package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta contendo o token de autenticação gerado após a validação do código")
public class LoginResponseDTO {

    @Schema(description = "Token de acesso no formato Bearer JWT (contém as permissões do usuário e expira em 24h)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
