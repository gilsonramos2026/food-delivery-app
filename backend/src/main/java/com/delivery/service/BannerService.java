package com.delivery.service;

import com.delivery.dto.request.BannerRequestDTO;
import com.delivery.dto.response.BannerResponseDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface BannerService {
    BannerResponseDTO create(BannerRequestDTO dto, MultipartFile file) throws IOException;
    List<BannerResponseDTO> findActiveBanners();
}
