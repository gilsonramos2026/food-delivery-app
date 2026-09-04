package com.delivery.controller;

import com.delivery.dto.request.ProductReviewRequestDTO;
import com.delivery.dto.request.ReviewRequestDTO;
import com.delivery.dto.response.ProductReviewResponseDTO;
import com.delivery.dto.response.ReviewResponseDTO;
import com.delivery.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews", description = "Endpoints para avaliações de pedidos e notas de produtos")
@SecurityRequirement(name = "Bearer Authentication")
public class AvaliacaoController {

    private final ReviewService reviewService;

    public AvaliacaoController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/order")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @Operation(summary = "Avaliar pedido concluído", description = "Envia a nota geral da experiência do pedido de delivery (nota de 1 a 5 separada do produto).")
    public ResponseEntity<ReviewResponseDTO> reviewOrder(@Valid @RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createOrderReview(dto));
    }

    @PostMapping("/product")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @Operation(summary = "Avaliar produto específico", description = "Envia a nota de qualidade para um item do cardápio.")
    public ResponseEntity<ProductReviewResponseDTO> reviewProduct(@Valid @RequestBody ProductReviewRequestDTO dto, @AuthenticationPrincipal String phone) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createProductReview(dto, phone));
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('CLIENT', 'COURIER', 'ADMIN')")
    @Operation(summary = "Listar avaliações de um produto", description = "Recupera todas as opiniões e notas deixadas para um prato específico do menu.")
    public ResponseEntity<List<ProductReviewResponseDTO>> getProductReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.findByProductId(productId));
    }
}
