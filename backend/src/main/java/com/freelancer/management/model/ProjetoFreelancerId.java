package com.freelancer.management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe de Chave Composta para ProjetoFreelancer
 * 
 * IMPORTANTE: Toda chave composta em JPA deve:
 * 1. Implementar Serializable
 * 2. Ter construtor vazio
 * 3. Sobrescrever equals() e hashCode()
 * 
 * Esta classe representa a chave primária composta: (projeto_id, freelancer_id)
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoFreelancerId implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "projeto_id")
    private Long projetoId;

    @Column(name = "freelancer_id")
    private Long freelancerId;

    // ========== EQUALS E HASHCODE (OBRIGATÓRIO PARA CHAVE COMPOSTA) ==========
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjetoFreelancerId that = (ProjetoFreelancerId) o;
        return Objects.equals(projetoId, that.projetoId) && 
               Objects.equals(freelancerId, that.freelancerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projetoId, freelancerId);
    }
}