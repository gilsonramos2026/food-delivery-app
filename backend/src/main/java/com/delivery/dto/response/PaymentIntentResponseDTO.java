package com.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Dados de retorno para inicialização do pagamento no Frontend")
public class PaymentIntentResponseDTO {

    @Schema(description = "Chave secreta do cliente para renderizar o Stripe Elements", example = "pi_3MtwXwLkdIwHu7ix1_secret_v6fg...")
    private String clientSecret;
}

