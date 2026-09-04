package com.delivery.controller;

import com.delivery.dto.response.UserResponseDTO;
import com.delivery.model.enums.Role;
import com.delivery.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/usuarios")
@Tag(name = "User Management", description = "Endpoints administrativos para gerenciamento de usuários")
@SecurityRequirement(name = "Bearer Authentication") // Vincula a necessidade de token JWT na documentação do Swagger
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os usuários", description = "Retorna a listagem de todos os usuários cadastrados na plataforma. Acesso restrito a administradores.")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Promover ou alterar papéis", description = "Permite alterar o nível de acesso (Role) de um usuário específico utilizando seu ID. Acesso restrito a administradores.")
    public ResponseEntity<UserResponseDTO> updateRole(@PathVariable Long id, @RequestParam Role role) {
        return ResponseEntity.ok(userService.updateRole(id, role));
    }
}
