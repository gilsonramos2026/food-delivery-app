package com.delivery.repository;

import com.delivery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Essencial para filtrar o cardápio por categoria
    List<Product> findByCategoryId(Long categoryId);
}
