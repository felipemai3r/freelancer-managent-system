package com.freelancer.management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "projeto_freelancer")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoFreelancer {

    @OneToOne(cascade = CascadeType.PERSIST) // Relação um-para-um com Pessoa. Remover cascade.
    @MapsId // Usa o mesmo ID de Pessoa
    @JoinColumn(name = "id") // Chave estrangeira para Pessoa
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Freelancer freelancer;

    @Column(name = "papel", nullable = false)
    private String papel;
    
    @Column(name = "valor_acordado", precision = 10, scale = 2)
    private BigDecimal valorAcordado;

    @Column(name = "atribuido_em", nullable = false, updatable = false)
     private LocalDateTime atriibuidoEm;


     
}

/**
 * CREATE TABLE IF NOT EXISTS projeto_freelancer (
    projeto_id BIGINT NOT NULL REFERENCES projeto(id) ON DELETE CASCADE,
    freelancer_id BIGINT NOT NULL REFERENCES freelancer(id) ON DELETE CASCADE,
    papel VARCHAR(100) NOT NULL,
    valor_acordado DECIMAL(10,2),
    atribuido_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (projeto_id, freelancer_id)
 */
