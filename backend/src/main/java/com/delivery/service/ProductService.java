package com.delivery.service;

import com.delivery.mapper.ProductMapper;
import com.delivery.model.Category;
import com.delivery.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final String uploadDir = "uploads/";

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO saveWithImage(ProductRequestDTO requestDTO, MultipartFile imageFile) throws IOException {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + requestDTO.getCategoryId()));

        Product product = productMapper.toEntity(requestDTO, category);

        if (imageFile != null && !imageFile.isEmpty()) {
            Path directoryPath = Paths.get(uploadDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String originalFilename = imageFile.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            Path filePath = directoryPath.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), filePath);

            product.setImageUrl("/uploads/" + uniqueFilename);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }
}