package com.delivery.mapper;

import com.delivery.dto.response.ReviewResponseDTO;
import com.delivery.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponseDTO toResponseDTO(Review entity) {
        if (entity == null) return null;
        return ReviewResponseDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .rating(entity.getRating())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
