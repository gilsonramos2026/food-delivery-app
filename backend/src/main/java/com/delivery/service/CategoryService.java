package com.delivery.service;

import com.delivery.dto.request.CategoryRequestDTO;
import com.delivery.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO save(CategoryRequestDTO requestDTO);
}
