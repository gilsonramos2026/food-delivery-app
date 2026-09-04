package com.delivery.service.impl;

import com.delivery.dto.response.PaymentIntentResponseDTO;
import com.delivery.exception.BusinessException;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.model.Order;
import com.delivery.model.enums.OrderStatus;
import com.delivery.repository.OrderRepository;
import com.delivery.service.OrderService;
import com.delivery.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.stripe.exception.StripeException;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    public PaymentServiceImpl(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.stripeSecretKey;
    }

    @Override
    @Transactional
    public PaymentIntentResponseDTO createPaymentIntent(Long orderId) throws StripeException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + orderId));

        if (order.getStatus() != OrderStatus.RECEBIDO) {
            throw new BusinessException("Só é possível pagar pedidos que estão no status RECEBIDO.");
        }

        // O Stripe calcula em centavos (Ex: R$ 45.90 vira 4590)
        long amountInCents = order.getTotal().multiply(BigDecimal.valueOf(100)).longValue();

        // CRUCIAL: Salvar o orderId no Metadata para o Webhook identificar depois
        Map<String, String> metadata = new HashMap<>();
        metadata.put("orderId", orderId.toString());

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("brl")
                .putAllMetadata(metadata)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return new PaymentIntentResponseDTO(paymentIntent.getClientSecret());
    }

    @Override
    @Transactional
    public void handleWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            if ("payment_intent.succeeded".equals(event.getType())) {
                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

                if (dataObjectDeserializer.getObject().isPresent()) {
                    PaymentIntent paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();

                    // Recupera o ID do pedido gravado no metadata do Stripe
                    String orderIdStr = paymentIntent.getMetadata().get("orderId");

                    if (orderIdStr != null) {
                        Long orderId = Long.parseLong(orderIdStr);

                        // FLUXO AUTOMÁTICO: Avança o status do pedido para PREPARANDO após a confirmação
                        orderService.advanceStatus(orderId, OrderStatus.PREPARANDO);
                        System.out.println("[STRIPE WEBHOOK] Pagamento confirmado para o Pedido #" + orderId);
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException("Falha ao processar o webhook do Stripe: " + e.getMessage());
        }
    }
}
