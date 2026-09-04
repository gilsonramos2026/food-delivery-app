package com.delivery.controller;

import com.delivery.dto.request.LocalizacaoDTO;
import com.delivery.service.NotificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveries/{orderId}")
public class EntregaController {

    private final NotificacaoService notificacaoService;

    public EntregaController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @PostMapping("/location")
    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    public ResponseEntity<Void> updateLocation(@PathVariable Long orderId, @RequestBody LocalizacaoDTO location) {
        notificacaoService.sendLiveLocation(orderId, location);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/arrival")
    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    public ResponseEntity<Void> notifyArrival(@PathVariable Long orderId) {
        notificacaoService.notifyDeliveryArrival(orderId);
        return ResponseEntity.ok().build();
    }
}
