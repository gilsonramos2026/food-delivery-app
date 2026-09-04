package com.delivery.service;

import com.delivery.dto.response.FidelityResponseDTO;

public interface FidelityService {
    void addOrderProgress(Long userId);
    FidelityResponseDTO getStatusByPhone(String userPhone);
}
