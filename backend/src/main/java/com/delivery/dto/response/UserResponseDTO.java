package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados de retorno do perfil do usuário")
public class UserResponseDTO {

    @Schema(description = "ID único do usuário gerado no banco de dados", example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário (pode retornar nulo no primeiro acesso)", example = "João Silva")
    private String name;

    @Schema(description = "Endereço de e-mail cadastrado (pode retornar um placeholder baseado no telefone no primeiro acesso)", example = "11999999999@delivery.com")
    private String email;

    @Schema(description = "Número do telefone celular com DDD usado para autenticação", example = "11999999999")
    private String phone;

    @Schema(description = "Papel ou nível de acesso atual do usuário no sistema", example = "CLIENT", allowableValues = {"CLIENT", "COURIER", "ADMIN"})
    private String role;

    @Schema(description = "Data e hora exata da criação da conta", example = "2026-09-04T13:46:23")
    private LocalDateTime createdAt;
}
