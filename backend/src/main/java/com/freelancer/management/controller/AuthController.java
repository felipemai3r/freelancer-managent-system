package com.freelancer.management.controller;

import com.freelancer.management.dto.LoginRequest;
import com.freelancer.management.dto.LoginResponse;
import com.freelancer.management.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST /api/auth/login
     * 
     * Realiza autenticação do usuário
     * 
     * @param request { email, senha }
     * @return { token, user: { id, email, tipoUsuario } }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(401)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Classe interna para respostas de erro
     */
    record ErrorResponse(String message) {}
}