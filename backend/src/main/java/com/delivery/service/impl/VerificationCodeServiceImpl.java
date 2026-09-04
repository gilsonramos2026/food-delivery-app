package com.delivery.service.impl;

import com.delivery.exception.BusinessException;
import com.delivery.model.VerificationCode;
import com.delivery.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository repository;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    public VerificationCodeServiceImpl(VerificationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void sendCode(String phone) {
        String code = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);

        VerificationCode verificationCode = VerificationCode.builder()
                .phone(phone)
                .code(code)
                .expiresAt(expiresAt)
                .build();

        repository.save(verificationCode);

        if ("prod".equals(activeProfile)) {
            System.out.println("[TWILIO PROD] Enviando SMS para " + phone + ": " + code);
        } else {
            System.out.println("[DEMO CONSOLE] Código SMS para " + phone + ": " + code);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyCode(String phone, String code) {
        Optional<VerificationCode> opt = repository.findTopByPhoneOrderByExpiresAtDesc(phone);

        if (opt.isEmpty()) {
            throw new BusinessException("Nenhum código foi solicitado para este telefone.");
        }

        VerificationCode verificationCode = opt.get();

        if (verificationCode.isExpired()) {
            throw new BusinessException("O código de verificação expirou.");
        }

        return verificationCode.getCode().equals(code);
    }
}
