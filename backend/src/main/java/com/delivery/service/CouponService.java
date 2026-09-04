package com.delivery.service;

import com.delivery.dto.request.CouponRequestDTO;
import com.delivery.dto.response.CouponResponseDTO;
import com.delivery.model.Coupon;
import java.math.BigDecimal;
import java.util.List;

public interface CouponService {
    CouponResponseDTO create(CouponRequestDTO dto);
    List<CouponResponseDTO> findAll();
    Coupon validateAndGetCoupon(String code, BigDecimal orderValue);
}
