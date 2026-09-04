package com.delivery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @NotNull(message = "O preço de venda é obrigatório")
    @PositiveOrZero(message = "O preço de venda deve ser maior ou igual a zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "O preço de custo é obrigatório")
    @PositiveOrZero(message = "O preço de custo deve ser maior ou igual a zero")
    @Column(name = "cost_price", precision = 10, scale = 2)
    private BigDecimal costPrice;

    private String imageUrl;

    @NotNull(message = "A categoria é obrigatória")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
