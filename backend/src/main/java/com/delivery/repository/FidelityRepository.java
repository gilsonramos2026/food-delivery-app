package com.delivery.repository;

import com.delivery.model.Fidelity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FidelityRepository extends JpaRepository<Fidelity, Long> {
    Optional<Fidelity> findByUserId(Long userId);
}
