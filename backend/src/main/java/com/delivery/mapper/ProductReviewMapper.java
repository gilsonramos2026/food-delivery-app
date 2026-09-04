package com.delivery.mapper;

import com.delivery.dto.response.ProductReviewResponseDTO;
import com.delivery.model.ProductReview;
import org.springframework.stereotype.Component;

@Component
public class ProductReviewMapper {

    public ProductReviewResponseDTO toResponseDTO(ProductReview entity) {
        if (entity == null) return null;
        return ProductReviewResponseDTO.builder()
                .id(entity.getId())
                .userName(entity.getUser().getName() != null ? entity.getUser().getName() : "Usuário")
                .productId(entity.getProduct().getId())
                .rating(entity.getRating())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
