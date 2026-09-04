package com.delivery.controller;

import com.delivery.dto.request.BannerRequestDTO;
import com.delivery.dto.response.BannerResponseDTO;
import com.delivery.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/banners")
@Tag(name = "Banners", description = "Endpoints para exibição e gerenciamento de carrossel promocional")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    @Operation(summary = "Listar banners ativos", description = "Retorna a lista de carrossel promocional ordenado por display_order. Rota pública para a Home.")
    public ResponseEntity<List<BannerResponseDTO>> getActiveBanners() {
        return ResponseEntity.ok(bannerService.findActiveBanners());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Criar novo banner com upload (Admin)", description = "Adiciona uma imagem de promoção e define sua ordem de exibição. Restrito a administradores.")
    public ResponseEntity<BannerResponseDTO> create(
            @Valid @ModelAttribute BannerRequestDTO dto,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bannerService.create(dto, file));
    }
}
