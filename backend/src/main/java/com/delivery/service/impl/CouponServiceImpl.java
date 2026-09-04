package com.delivery.service.impl;

import com.delivery.dto.request.CouponRequestDTO;
import com.delivery.dto.response.CouponResponseDTO;
import com.delivery.exception.BusinessException;
import com.delivery.mapper.CouponMapper;
import com.delivery.model.Coupon;
import com.delivery.repository.CouponRepository;
import com.delivery.service.CouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    public CouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    @Transactional
    public CouponResponseDTO create(CouponRequestDTO dto) {
        if (couponRepository.findByCodeIgnoreCase(dto.getCode()).isPresent()) {
            throw new BusinessException("Já existe um cupom cadastrado com este código.");
        }
        Coupon coupon = couponMapper.toEntity(dto);
        return couponMapper.toResponseDTO(couponRepository.save(coupon));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponResponseDTO> findAll() {
        return couponRepository.findAll().stream()
                .map(couponMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public Coupon validateAndGetCoupon(String code, BigDecimal orderValue) {
        Coupon coupon = couponRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new BusinessException("Cupom não encontrado ou inválido."));

        // Regra de Negócio: isValidoPara() -> Ativo + Validade + Usos + Valor Mínimo
        if (!coupon.getActive()) {
            throw new BusinessException("Este cupom está desativado.");
        }
        if (coupon.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Este cupom já expirou.");
        }
        if (coupon.getCurrentUses() >= coupon.getMaxUses()) {
            throw new BusinessException("Este cupom atingiu o limite máximo de utilizações.");
        }
        if (orderValue.compareTo(coupon.getMinOrderValue()) < 0) {
            throw new BusinessException("O valor mínimo do pedido para aplicar este cupom é R$ " + coupon.getMinOrderValue());
        }

        return coupon;
    }
}
