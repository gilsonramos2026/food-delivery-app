package com.delivery.model;

import com.delivery.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(length = 100) // Removido o nullable = false para permitir criação automática apenas com telefone
    private String name;

    @Email(message = "Formato de e-mail inválido")
    @Column(unique = true, length = 180) // Removido o nullable = false para o primeiro login
    private String email;

    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @Column // Senha torna-se opcional já que a autenticação principal será por código OTP
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "O telefone é obrigatório")
    @Column(nullable = false, unique = true, length = 20) // Telefone continua estrito e obrigatório
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) {
            this.role = Role.CLIENT;
        }
    }
}
