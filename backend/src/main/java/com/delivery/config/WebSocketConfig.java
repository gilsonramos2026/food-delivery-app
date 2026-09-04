package com.delivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // ESTA ANOTAÇÃO CRIA O BEAN SIMPMESSAGINGTEMPLATE
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint que o frontend e os aplicativos conectam para iniciar a conexão
        registry.addEndpoint("/ws-delivery")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Habilita o broker para tópicos de escuta (Outbound)
        registry.enableSimpleBroker("/topico");

        // Prefixo para mensagens enviadas ao servidor (Inbound)
        registry.setApplicationDestinationPrefixes("/app");
    }
}
