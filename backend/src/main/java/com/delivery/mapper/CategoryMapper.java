package com.delivery.mapper;

import com.delivery.dto.request.CategoryRequestDTO;
import com.delivery.dto.response.CategoryResponseDTO;
import com.delivery.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO dto) {
        if (dto == null) return null;
        Category category = new Category();
        category.setName(dto.getName());
        category.setDisplayOrder(dto.getDisplayOrder());
        return category;
    }

    public CategoryResponseDTO toResponseDTO(Category category) {
        if (category == null) return null;
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDisplayOrder(category.getDisplayOrder());
        return dto;
    }
}
