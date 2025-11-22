package com.freelancer.management.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.freelancer.management.model.enums.TipoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "pessoa")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    //@JsonIgnore //Ignora a senha na serialização JSON 
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipo;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "ativo")
    private Boolean ativo = true;

    public boolean isAdmin() {
        return this.tipo == TipoUsuario.ADMIN;
    }

    /**
     * Verifica se é empresa
     */
    public boolean isEmpresa() {
        return this.tipo == TipoUsuario.EMPRESA;
    }

    /**
     * Verifica se é freelancer
     */
    public boolean isFreelancer() {
        return this.tipo == TipoUsuario.FREELANCER;
    }


    public boolean validarEmail(String email) {
        if( email == null || email.isEmpty() ) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean validarSenha(String senha) {
        if( senha == null || senha.isEmpty() || senha.length() < 8) {
            return false;
        }
        return this.senha.equals(senha);
    }

    /**
     * Desativa o usuário (soft delete)
     */
    public void desativar() {
        this.ativo = false;
    }

    /**
     * Reativa o usuário
     */
    public void ativar() {
        this.ativo = true;
    }

    /**
     * Verifica se o usuário está ativo
     */
    public boolean isAtivo() {
        return Boolean.TRUE.equals(this.ativo);
    }

    /**
     * Verifica se o usuário está inativo
     */
    public boolean isInativo() {
        return Boolean.FALSE.equals(this.ativo);
    }
}
