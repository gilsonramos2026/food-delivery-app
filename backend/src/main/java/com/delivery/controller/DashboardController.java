package com.delivery.controller;

import com.delivery.dto.response.DashboardResponseDTO;
import com.delivery.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Dashboard metrics", description = "Métricas gerenciais consolidadas exclusivas para o administrador")
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Operation(summary = "Carregar consolidação financeira e top produtos", description = "Calcula faturamento total bruto, lucro líquido real (Preço de Venda - Preço de Custo) e elenca o top 5 produtos do mês.")
    public ResponseEntity<DashboardResponseDTO> getDashboardData() {
        return ResponseEntity.ok(dashboardService.getAdminDashboardData());
    }
}
