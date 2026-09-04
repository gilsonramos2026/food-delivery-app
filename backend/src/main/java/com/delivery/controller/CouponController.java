package com.delivery.controller;

import com.delivery.dto.request.CouponRequestDTO;
import com.delivery.dto.response.CouponResponseDTO;
import com.delivery.model.Coupon;
import com.delivery.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/coupons")
@Tag(name = "Coupons", description = "Endpoints para gerenciamento e validação de cupons de desconto")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Criar novo cupom (Admin)", description = "Cadastra um novo cupom de desconto na plataforma. Acesso restrito a administradores.")
    public ResponseEntity<CouponResponseDTO> create(@Valid @RequestBody CouponRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(couponService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Listar todos os cupons (Admin)", description = "Retorna o histórico e logs de todos os cupons criados. Acesso restrito.")
    public ResponseEntity<List<CouponResponseDTO>> getAll() {
        return ResponseEntity.ok(couponService.findAll());
    }

    @GetMapping("/validate")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Validar cupom antes de aplicar", description = "Verifica se o código está ativo, dentro da validade, abaixo do limite de uso e atinge o valor mínimo.")
    public ResponseEntity<Coupon> validate(@RequestParam String code, @RequestParam BigDecimal orderValue) {
        return ResponseEntity.ok(couponService.validateAndGetCoupon(code, orderValue));
    }
}
