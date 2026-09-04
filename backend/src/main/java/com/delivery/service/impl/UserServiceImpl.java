package com.delivery.service.impl;

import com.delivery.dto.request.UserRequestDTO;
import com.delivery.dto.response.UserResponseDTO;
import com.delivery.exception.BusinessException;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.mapper.UserMapper;
import com.delivery.model.User;
import com.delivery.model.enums.Role;
import com.delivery.repository.UserRepository; // Verifique se seu repository está nessa pasta
import com.delivery.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            throw new BusinessException("Telefone já cadastrado no sistema.");
        }
        if (userRepository.existsByEmail(dto.getEmail())) { // Adicione existsByEmail no seu UserRepository se necessário
            throw new BusinessException("E-mail já cadastrado no sistema.");
        }

        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDTO updateRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

        user.setRole(role);
        return UserMapper.toResponseDTO(userRepository.save(user));
    }
}

