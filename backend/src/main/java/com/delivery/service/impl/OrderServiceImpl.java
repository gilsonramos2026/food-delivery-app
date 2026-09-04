package com.delivery.service.impl;

import com.delivery.dto.request.OrderRequestDTO;
import com.delivery.dto.response.OrderResponseDTO;
import com.delivery.exception.BusinessException;
import com.delivery.exception.InvalidStatusTransitionException;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.mapper.OrderMapper;
import com.delivery.model.*;
import com.delivery.model.enums.OrderStatus;
import com.delivery.repository.OrderRepository;
import com.delivery.repository.ProductRepository;
import com.delivery.repository.UserRepository;
import com.delivery.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    // Definição estrita da máquina de estados por meio de um Mapa de transições permitidas
    private static final Map<OrderStatus, Set<OrderStatus>> VALID_TRANSITIONS = new EnumMap<>(OrderStatus.class);

    static {
        VALID_TRANSITIONS.put(OrderStatus.RECEBIDO, Set.of(OrderStatus.PREPARANDO, OrderStatus.CANCELADO));
        VALID_TRANSITIONS.put(OrderStatus.PREPARANDO, Set.of(OrderStatus.SAIU_PARA_ENTREGA, OrderStatus.CANCELADO));
        VALID_TRANSITIONS.put(OrderStatus.SAIU_PARA_ENTREGA, Set.of(OrderStatus.ENTREGUE));
        VALID_TRANSITIONS.put(OrderStatus.ENTREGUE, Collections.emptySet()); // Estado final, não sai
        VALID_TRANSITIONS.put(OrderStatus.CANCELADO, Collections.emptySet()); // Estado final, não sai
    }

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO, String clientPhone) {
        User client = userRepository.findByPhone(clientPhone)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não localizado."));

        Order order = new Order();
        order.setClient(client);
        order.setAddress(requestDTO.getAddress());
        order.setDeliveryFee(requestDTO.getDeliveryFee());
        order.setScheduledAt(requestDTO.getScheduledAt());
        order.setStatus(OrderStatus.RECEBIDO);

        BigDecimal subtotal = BigDecimal.ZERO;

        for (var itemDTO : requestDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não localizado com o ID: " + itemDTO.getProductId()));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice()); // Captura o preço fixo no momento da venda

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            subtotal = subtotal.add(itemTotal);
            order.getItems().add(item);
        }

        order.setTotal(subtotal.add(order.getDeliveryFee()));
        return orderMapper.toResponseDTO(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponseDTO advanceStatus(Long orderId, OrderStatus nextStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não localizado."));

        Set<OrderStatus> allowed = VALID_TRANSITIONS.get(order.getStatus());

        if (allowed == null || !allowed.contains(nextStatus)) {
            throw new InvalidStatusTransitionException("Não é permitido alterar o status de " + order.getStatus() + " para " + nextStatus);
        }

        order.setStatus(nextStatus);

        // Em um cenário de produção real, os disparos de WebSocket e acionamento de fidelidade rodariam aqui.
        System.out.println("[NOTIFICAÇÃO] Pedido #" + orderId + " mudou para o status: " + nextStatus);

        return orderMapper.toResponseDTO(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(Long orderId, String clientPhone, String reason, boolean isAdmin) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não localizado."));

        // Proteção de dono: Clientes normais só podem cancelar seus próprios pedidos
        if (!isAdmin && !order.getClient().getPhone().equals(clientPhone)) {
            throw new BusinessException("Acesso negado. Você não possui autorização para cancelar este pedido.");
        }

        Set<OrderStatus> allowed = VALID_TRANSITIONS.get(order.getStatus());
        if (allowed == null || !allowed.contains(OrderStatus.CANCELADO)) {
            throw new InvalidStatusTransitionException("O pedido não pode ser cancelado no status atual: " + order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELADO);
        order.setCancellationReason(reason != null ? reason : "Cancelado via sistema.");
        return orderMapper.toResponseDTO(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findByClient(String clientPhone) {
        User client = userRepository.findByPhone(clientPhone)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não localizado."));
        return orderRepository.findByClientId(client.getId()).stream().map(orderMapper::toResponseDTO).collect(Collectors.toList());
    }
}

