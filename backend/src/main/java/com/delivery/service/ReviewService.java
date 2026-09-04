package com.delivery.service;

import com.delivery.dto.request.ProductReviewRequestDTO;
import com.delivery.dto.request.ReviewRequestDTO;
import com.delivery.dto.response.ProductReviewResponseDTO;
import com.delivery.dto.response.ReviewResponseDTO;
import java.util.List;

public interface ReviewService {
    ReviewResponseDTO createOrderReview(ReviewRequestDTO dto);
    ProductReviewResponseDTO createProductReview(ProductReviewRequestDTO dto, String userPhone);
    List<ProductReviewResponseDTO> findByProductId(Long productId);
}
