package com.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados necessários para realizar a validação do código OTP e efetuar o login")
public class VerifyCodeRequestDTO {

    @Schema(description = "Número do telefone celular com DDD (apenas números)", example = "11999999999", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O telefone é obrigatório")
    private String phone;

    @Schema(description = "Código de verificação de 6 dígitos recebido por SMS/Console", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O código é obrigatório")
    @Size(min = 6, max = 6, message = "O código deve conter exatamente 6 dígitos")
    private String code;
}
