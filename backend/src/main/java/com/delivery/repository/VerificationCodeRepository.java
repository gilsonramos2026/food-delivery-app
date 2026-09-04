package com.delivery.repository;

import com.delivery.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    // Busca o último código gerado para o telefone informado para validar a expiração de 5 minutos
    Optional<VerificationCode> findTopByPhoneOrderByExpiresAtDesc(String phone);
}

