package com.delivery.mapper;

import com.delivery.dto.request.CouponRequestDTO;
import com.delivery.dto.response.CouponResponseDTO;
import com.delivery.model.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public Coupon toEntity(CouponRequestDTO dto) {
        if (dto == null) return null;
        Coupon coupon = new Coupon();
        coupon.setCode(dto.getCode());
        coupon.setDiscountValue(dto.getDiscountValue());
        coupon.setMinOrderValue(dto.getMinOrderValue());
        coupon.setExpiresAt(dto.getExpiresAt());
        coupon.setMaxUses(dto.getMaxUses());
        return coupon;
    }

    public CouponResponseDTO toResponseDTO(Coupon entity) {
        if (entity == null) return null;
        return CouponResponseDTO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .discountValue(entity.getDiscountValue())
                .minOrderValue(entity.getMinOrderValue())
                .active(entity.getActive())
                .expiresAt(entity.getExpiresAt())
                .maxUses(entity.getMaxUses())
                .currentUses(entity.getCurrentUses())
                .build();
    }
}
