package com.delivery.mapper;

import com.delivery.dto.request.UserRequestDTO;
import com.delivery.dto.response.UserResponseDTO;
import com.delivery.model.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();
    }

    public static UserResponseDTO toResponseDTO(User entity) {
        if (entity == null) {
            return null;
        }
        return UserResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .role(entity.getRole() != null ? entity.getRole().name() : null)
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

