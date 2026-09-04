package com.delivery.controller;

import com.delivery.dto.request.ProductRequestDTO;
import com.delivery.dto.response.ProductResponseDTO;
import com.delivery.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDTO> listAll() {
        return productService.findAll();
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDTO> listByCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> create(
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam(value = "costPrice", required = false) BigDecimal costPrice,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {

        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName(name);
        requestDTO.setDescription(description);
        requestDTO.setPrice(price);
        requestDTO.setCostPrice(costPrice);
        requestDTO.setCategoryId(categoryId);

        ProductResponseDTO savedProduct = productService.saveWithImage(requestDTO, image);
        return ResponseEntity.ok(savedProduct);
    }
}