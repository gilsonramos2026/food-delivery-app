package com.delivery.controller;

import com.delivery.dto.response.PaymentIntentResponseDTO;
import com.delivery.service.PaymentService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Tag(name = "Payments", description = "Endpoints para pagamentos integrados com Stripe")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/orders/{id}/payment")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Criar intenção de pagamento", description = "Gera o clientSecret do Stripe para um pedido específico no status RECEBIDO.")
    public ResponseEntity<PaymentIntentResponseDTO> createPaymentIntent(@PathVariable Long id) throws StripeException {
        return ResponseEntity.ok(paymentService.createPaymentIntent(id));
    }

    @PostMapping("/payments/webhook")
    @Operation(summary = "Webhook de confirmação do Stripe", description = "Endpoint público consumido pelo Stripe para avisar quando um pagamento foi concluído.")
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        paymentService.handleWebhook(payload, sigHeader);
        return ResponseEntity.ok().build();
    }
}
