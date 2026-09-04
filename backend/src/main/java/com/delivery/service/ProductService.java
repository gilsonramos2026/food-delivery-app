package com.delivery.service;

import com.delivery.dto.request.ProductRequestDTO;
import com.delivery.dto.response.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll();
    List<ProductResponseDTO> findByCategory(Long categoryId);
    ProductResponseDTO saveWithImage(ProductRequestDTO requestDTO, MultipartFile imageFile) throws IOException;
}
