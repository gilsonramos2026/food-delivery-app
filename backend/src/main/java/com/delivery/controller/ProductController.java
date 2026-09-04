package com.delivery.controller;

import com.delivery.dto.request.ProductRequestDTO;
import com.delivery.dto.response.ProductResponseDTO;
import com.delivery.service.ProductService;
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
@RequestMapping("/products")
@Tag(name = "Products", description = "Endpoints para visualização e gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna a listagem completa de itens cadastrados no delivery. Rota pública.")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Filtrar produtos por categoria", description = "Retorna apenas os produtos vinculados ao ID da categoria informada. Rota pública.")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategory(categoryId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Criar novo produto com imagem", description = "Cadastra um produto vinculando-o a uma categoria e salvando a imagem em disco. Acesso restrito a administradores.")
    public ResponseEntity<ProductResponseDTO> create(
            @Valid @ModelAttribute ProductRequestDTO requestDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveWithImage(requestDTO, imageFile));
    }
}
