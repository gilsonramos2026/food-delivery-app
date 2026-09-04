package com.delivery.service;

import com.delivery.dto.request.UserRequestDTO;
import com.delivery.dto.response.UserResponseDTO;
import com.delivery.model.enums.Role;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserRequestDTO dto);
    List<UserResponseDTO> findAll();
    UserResponseDTO updateRole(Long id, Role role);
}

