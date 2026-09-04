package com.delivery.controller;

import com.delivery.dto.request.OrderRequestDTO;
import com.delivery.dto.response.OrderResponseDTO;
import com.delivery.model.enums.OrderStatus;
import com.delivery.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Endpoints para criação, acompanhamento e transição de estados dos pedidos")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @Operation(summary = "Criar novo pedido", description = "Abre um pedido para o cliente logado com base nos produtos informados.")
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO requestDTO, @AuthenticationPrincipal String phone) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(requestDTO, phone));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @Operation(summary = "Histórico do cliente", description = "Lista todos os pedidos feitos pelo cliente autenticado.")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(@AuthenticationPrincipal String phone) {
        return ResponseEntity.ok(orderService.findByClient(phone));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COURIER')")
    @Operation(summary = "Listar todos os pedidos (Admin/Courier)", description = "Retorna todos os pedidos da plataforma. Acesso restrito a administradores e entregadores.")
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'COURIER')")
    @Operation(summary = "Avançar status (Máquina de Estados)", description = "Altera o status do pedido seguindo as transições de fluxo permitidas.")
    public ResponseEntity<OrderResponseDTO> advanceStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.advanceStatus(id, status));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @Operation(summary = "Cancelar pedido", description = "Cancela um pedido ativo. Clientes só cancelam seus próprios pedidos e apenas se o status permitir.")
    public ResponseEntity<OrderResponseDTO> cancel(
            @PathVariable Long id,
            @RequestParam(required = false) String reason,
            @AuthenticationPrincipal String phone,
            @org.springframework.security.core.annotation.CurrentSecurityContext(expression = "authentication.authorities") java.util.Collection<?> authorities) {

        boolean isAdmin = authorities.stream().anyMatch(a -> a.toString().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(orderService.cancelOrder(id, phone, reason, isAdmin));
    }
}
