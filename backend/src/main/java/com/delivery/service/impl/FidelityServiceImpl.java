package com.delivery.service.impl;

import com.delivery.dto.response.FidelityResponseDTO;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.mapper.FidelityMapper;
import com.delivery.model.Fidelity;
import com.delivery.model.User;
import com.delivery.repository.FidelityRepository;
import com.delivery.repository.UserRepository;
import com.delivery.service.FidelityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FidelityServiceImpl implements FidelityService {

    private final FidelityRepository fidelityRepository;
    private final UserRepository userRepository;
    private final FidelityMapper fidelityMapper;

    public FidelityServiceImpl(FidelityRepository fidelityRepository, UserRepository userRepository, FidelityMapper fidelityMapper) {
        this.fidelityRepository = fidelityRepository;
        this.userRepository = userRepository;
        this.fidelityMapper = fidelityMapper;
    }

    @Override
    @Transactional
    public void addOrderProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));

        Fidelity fidelity = fidelityRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Fidelity newFidelity = new Fidelity();
                    newFidelity.setUser(user);
                    return newFidelity;
                });

        fidelity.setOrderCount(fidelity.getOrderCount() + 1);

        // Ao completar 10 pedidos entregues, libera brindeDisponivel (rewardAvailable)
        if (fidelity.getOrderCount() >= 10) {
            fidelity.setRewardAvailable(true);
            fidelity.setOrderCount(0); // Reseta a contagem para o próximo ciclo
        }

        fidelityRepository.save(fidelity);
    }

    @Override
    @Transactional(readOnly = true)
    public FidelityResponseDTO getStatusByPhone(String userPhone) {
        User user = userRepository.findByPhone(userPhone)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o telefone: " + userPhone));

        Fidelity fidelity = fidelityRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Fidelity newFidelity = new Fidelity();
                    newFidelity.setUser(user);
                    return newFidelity;
                });

        return fidelityMapper.toResponseDTO(fidelity);
    }
}
