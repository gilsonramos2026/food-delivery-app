package com.delivery.controller;

// DTOs de Entrada (Requests)
import com.delivery.dto.request.SendCodeRequestDTO;
import com.delivery.dto.request.UserRequestDTO;
import com.delivery.dto.request.VerifyCodeRequestDTO;

// DTOs de Saída (Responses)
import com.delivery.dto.response.LoginResponseDTO;
import com.delivery.dto.response.UserResponseDTO;

// Infraestrutura, Segurança e Domínio
import com.delivery.exception.BusinessException;
import com.delivery.model.User;
import com.delivery.repository.UserRepository;
import com.delivery.security.JwtService;
import com.delivery.service.UserService;
import com.delivery.service.VerificationCodeService;

// Swagger e Spring Framework
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para autenticação via código OTP")
public class AuthController {

    private final VerificationCodeService verificationService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(VerificationCodeService verificationService,
                          UserRepository userRepository,
                          JwtService jwtService,
                          UserService userService) {
        this.verificationService = verificationService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/enviar-codigo")
    @Operation(summary = "Solicitar código", description = "Gera e envia um código de verificação de 6 dígitos válido por 5 minutos via SMS/Console.")
    public ResponseEntity<Void> sendCode(@Valid @RequestBody SendCodeRequestDTO request) {
        verificationService.sendCode(request.getPhone());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verificar")
    @Operation(summary = "Verificar código e logar", description = "Valida o código OTP enviado. Se o usuário existir, retorna o token JWT para acesso à API.")
    public ResponseEntity<LoginResponseDTO> verifyCode(@Valid @RequestBody VerifyCodeRequestDTO request) {
        boolean isValid = verificationService.verifyCode(request.getPhone(), request.getCode());

        if (!isValid) {
            throw new BusinessException("Código de verificação incorreto.");
        }

        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new BusinessException("Usuário não cadastrado com este telefone. Crie sua conta primeiro."));

        String token = jwtService.generateToken(user.getPhone(), user.getRole().name());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar nova conta (Sign Up)", description = "Cadastra um novo cliente no sistema. Por padrão, todo usuário nasce com o papel de CLIENTE.")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }
}
