package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados necessários para solicitar o envio do código OTP por telefone")
public class SendCodeRequestDTO {

    @Schema(description = "Número do telefone celular com DDD (apenas números)", example = "11999999999", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O telefone é obrigatório")
    private String phone;
}
