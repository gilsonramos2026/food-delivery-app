package com.delivery.controller;

import com.delivery.dto.response.FidelityResponseDTO;
import com.delivery.service.FidelityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fidelity")
@Tag(name = "Fidelity", description = "Endpoints para acompanhamento do selo e prêmios do cartão fidelidade")
@SecurityRequirement(name = "Bearer Authentication")
public class FidelidadeController {

    private final FidelityService fidelityService;

    public FidelidadeController(FidelityService fidelityService) {
        this.fidelityService = fidelityService;
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @Operation(summary = "Ver status do meu cartão fidelidade", description = "Retorna a contagem atual de 0 a 10 de pedidos ENTREGUES e se há brindes prontos para resgate.")
    public ResponseEntity<FidelityResponseDTO> getMyStatus(@AuthenticationPrincipal String phone) {
        return ResponseEntity.ok(fidelityService.getStatusByPhone(phone));
    }
}
