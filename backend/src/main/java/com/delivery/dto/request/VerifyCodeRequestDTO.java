package com.delivery.dto.request;

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
public class VerifyCodeRequestDTO {

    @NotBlank(message = "O telefone é obrigatório")
    private String phone;

    @NotBlank(message = "O código é obrigatório")
    @Size(min = 6, max = 6, message = "O código deve conter exatamente 6 dígitos")
    private String code;
}

