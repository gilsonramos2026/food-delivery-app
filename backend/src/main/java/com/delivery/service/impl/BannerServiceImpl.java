package com.delivery.service.impl;

import com.delivery.dto.request.BannerRequestDTO;
import com.delivery.dto.response.BannerResponseDTO;
import com.delivery.exception.BusinessException;
import com.delivery.mapper.BannerMapper;
import com.delivery.model.Banner;
import com.delivery.repository.BannerRepository;
import com.delivery.service.BannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;
    private final String uploadDir = "uploads/";

    public BannerServiceImpl(BannerRepository bannerRepository, BannerMapper bannerMapper) {
        this.bannerRepository = bannerRepository;
        this.bannerMapper = bannerMapper;
    }

    @Override
    @Transactional
    public BannerResponseDTO create(BannerRequestDTO dto, MultipartFile file) throws IOException {
        Banner banner = bannerMapper.toEntity(dto);

        if (file != null && !file.isEmpty()) {
            Path directoryPath = Paths.get(uploadDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            Path filePath = directoryPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath);

            banner.setImageUrl("/uploads/" + uniqueFilename);
        } else {
            throw new BusinessException("O arquivo de imagem do banner é obrigatório.");
        }

        return bannerMapper.toResponseDTO(bannerRepository.save(banner));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerResponseDTO> findActiveBanners() {
        return bannerRepository.findByActiveTrueOrderByDisplayOrderAsc().stream()
                .map(bannerMapper::toResponseDTO)
                .toList();
    }
}
