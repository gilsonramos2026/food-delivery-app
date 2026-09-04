package com.delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {
    @NotBlank(message = "Category name is required")
    private String name;
    private Integer displayOrder;
}
