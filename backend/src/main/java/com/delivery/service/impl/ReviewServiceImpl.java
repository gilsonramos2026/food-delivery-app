package com.delivery.service.impl;

import com.delivery.dto.request.ProductReviewRequestDTO;
import com.delivery.dto.request.ReviewRequestDTO;
import com.delivery.dto.response.ProductReviewResponseDTO;
import com.delivery.dto.response.ReviewResponseDTO;
import com.delivery.exception.BusinessException;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.mapper.ProductReviewMapper;
import com.delivery.mapper.ReviewMapper;
import com.delivery.model.*;
import com.delivery.repository.*;
import com.delivery.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductReviewRepository productReviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;
    private final ProductReviewMapper productReviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductReviewRepository productReviewRepository,
                             OrderRepository orderRepository, ProductRepository productRepository,
                             UserRepository userRepository, ReviewMapper reviewMapper,
                             ProductReviewMapper productReviewMapper) {
        this.reviewRepository = reviewRepository;
        this.productReviewRepository = productReviewRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewMapper = reviewMapper;
        this.productReviewMapper = productReviewMapper;
    }

    @Override
    @Transactional
    public ReviewResponseDTO createOrderReview(ReviewRequestDTO dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + dto.getOrderId()));

        if (reviewRepository.existsById(dto.getOrderId())) {
            throw new BusinessException("Este pedido já foi avaliado anteriormente.");
        }

        Review review = new Review();
        review.setOrder(order);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        return reviewMapper.toResponseDTO(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public ProductReviewResponseDTO createProductReview(ProductReviewRequestDTO dto, String userPhone) {
        User user = userRepository.findByPhone(userPhone)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + dto.getProductId()));

        ProductReview review = new ProductReview();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        return productReviewMapper.toResponseDTO(productReviewRepository.save(review));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductReviewResponseDTO> findByProductId(Long productId) {
        // Observação: Adicione o método correspondente no ProductReviewRepository se necessário filtrar em lote
        return productReviewRepository.findAll().stream()
                .filter(r -> r.getProduct().getId().equals(productId))
                .map(productReviewMapper::toResponseDTO)
                .toList();
    }
}
