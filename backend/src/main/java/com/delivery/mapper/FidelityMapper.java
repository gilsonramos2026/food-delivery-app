package com.delivery.mapper;

import com.delivery.dto.response.FidelityResponseDTO;
import com.delivery.model.Fidelity;
import org.springframework.stereotype.Component;

@Component
public class FidelityMapper {

    public FidelityResponseDTO toResponseDTO(Fidelity entity) {
        if (entity == null) return null;
        return FidelityResponseDTO.builder()
                .orderCount(entity.getOrderCount())
                .rewardAvailable(entity.getRewardAvailable())
                .build();
    }
}
