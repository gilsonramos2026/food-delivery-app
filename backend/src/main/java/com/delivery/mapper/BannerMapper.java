package com.delivery.mapper;

import com.delivery.dto.request.BannerRequestDTO;
import com.delivery.dto.response.BannerResponseDTO;
import com.delivery.model.Banner;
import org.springframework.stereotype.Component;

@Component
public class BannerMapper {

    public Banner toEntity(BannerRequestDTO dto) {
        if (dto == null) return null;
        Banner banner = new Banner();
        banner.setTitle(dto.getTitle());
        banner.setDisplayOrder(dto.getDisplayOrder());
        return banner;
    }

    public BannerResponseDTO toResponseDTO(Banner entity) {
        if (entity == null) return null;
        return BannerResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .imageUrl(entity.getImageUrl())
                .displayOrder(entity.getDisplayOrder())
                .active(entity.getActive())
                .build();
    }
}
