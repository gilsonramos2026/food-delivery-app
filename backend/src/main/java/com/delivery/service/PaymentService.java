package com.delivery.service;

import com.delivery.dto.response.PaymentIntentResponseDTO;
import com.stripe.exception.StripeException; // ADICIONE ESTE IMPORT

public interface PaymentService {
    PaymentIntentResponseDTO createPaymentIntent(Long orderId) throws StripeException; // GARANTA O THROWS AQUI
    void handleWebhook(String payload, String sigHeader);
}

