package com.freelancer.management.service;

import com.freelancer.management.dto.LoginRequest;
import com.freelancer.management.dto.LoginResponse;
import com.freelancer.management.dto.UserDTO;
import com.freelancer.management.model.Pessoa;
import com.freelancer.management.repository.PessoaRepository;
import com.freelancer.management.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Realiza o login do usuário
     * 
     * @param request Dados de login (email e senha)
     * @return LoginResponse com token e dados do usuário
     * @throws RuntimeException se credenciais inválidas
     */
    public LoginResponse login(LoginRequest request) {
        // 1. Buscar pessoa por email
        Pessoa pessoa = pessoaRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        // 2. Verificar se está ativo
        if (!pessoa.isAtivo()) {
            throw new RuntimeException("Usuário desativado");
        }

        // 3. Verificar senha (compara plain text com hash BCrypt)
        if (!passwordEncoder.matches(request.getSenha(), pessoa.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        // 4. Gerar token JWT
        String token = jwtUtil.generateToken(
                pessoa.getId(),
                pessoa.getEmail(),
                pessoa.getTipo().name()
        );

        // 5. Montar resposta
        UserDTO userDTO = new UserDTO(
                pessoa.getId(),
                pessoa.getEmail(),
                pessoa.getTipo()
        );

        return new LoginResponse(token, userDTO);
    }
}