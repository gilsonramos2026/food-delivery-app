package com.delivery.mapper;

import com.delivery.dto.request.ProductRequestDTO;
import com.delivery.dto.response.ProductResponseDTO;
import com.delivery.model.Category;
import com.delivery.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public Product toEntity(ProductRequestDTO dto, Category category) {
        if (dto == null) return null;
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCostPrice(dto.getCostPrice());
        product.setCategory(category);
        return product;
    }

    public ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) return null;
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCostPrice(product.getCostPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(categoryMapper.toResponseDTO(product.getCategory()));
        return dto;
    }
}
