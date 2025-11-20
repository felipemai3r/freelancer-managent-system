package com.freelancer.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de Segurança do Spring Security
 * 
 * Por enquanto, libera todos os endpoints para desenvolvimento inicial.
 * Depois implementaremos JWT Authentication completo.
 * 
 * @author Felipe Maier
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuração da cadeia de filtros de segurança
     * 
     * Define quais endpoints são públicos e quais precisam autenticação.
     * Configurado para API REST stateless (sem sessões).
     * 
     * @param http HttpSecurity para configurar segurança
     * @return SecurityFilterChain configurada
     * @throws Exception em caso de erro na configuração
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desabilitar CSRF (não precisamos para API REST stateless)
            .csrf(csrf -> csrf.disable())
            
            // Configurar autorização de requisições
            .authorizeHttpRequests(auth -> auth
                // Liberar Actuator (health, info, metrics)
                .requestMatchers("/actuator/**").permitAll()
                
                // Liberar endpoints de autenticação (quando criar)
                .requestMatchers("/api/auth/**").permitAll()
                
                // Liberar Swagger/OpenAPI (quando configurar)
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Por enquanto, liberar tudo para desenvolvimento
                // TODO: Após implementar JWT, trocar para .authenticated()
                .anyRequest().permitAll()
            )
            
            // Sessões stateless (para API REST)
            // Não mantemos sessão no servidor, apenas validamos JWT
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }

    /**
     * Bean do encoder de senha (BCrypt)
     * 
     * Usado para:
     * - Criptografar senhas ao cadastrar usuários
     * - Validar senhas no login
     * 
     * BCrypt é seguro e recomendado para senhas.
     * 
     * @return PasswordEncoder configurado com BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}