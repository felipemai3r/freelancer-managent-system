package com.freelancer.management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entidade ProjetoFreelancer - Tabela associativa N:N entre Projeto e Freelancer
 * 
 * Esta é uma tabela de relacionamento MUITOS-PARA-MUITOS com atributos adicionais.
 * Usa chave composta (ProjetoFreelancerId) porque um projeto pode ter vários freelancers
 * e um freelancer pode participar de vários projetos.
 * 
 * Importante: Esta classe usa @EmbeddedId para chave composta (padrão JPA correto)
 * 
 * @author Felipe Maier
 * @version 1.0
 */


@Table(name = "projeto_freelancer")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoFreelancer {

    @EmbeddedId
    private ProjetoFreelancerId id;

    // ========== RELACIONAMENTOS ==========

    @ManyToOne
    @MapsId("projetoId") // ← Mapeia para o campo 'projetoId' dentro de ProjetoFreelancerId
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @ManyToOne
    @MapsId("freelancerId") // ← Mapeia para o campo 'freelancerId' dentro de ProjetoFreelancerId
    @JoinColumn(name = "freelancer_id", nullable = false)
    private Freelancer freelancer;

    // ========== ATRIBUTOS ADICIONAIS ==========

    @Column(name = "papel", nullable = false, length = 100)
    private String papel; // Ex: "Designer Principal", "Desenvolvedor Backend"

    @Column(name = "valor_acordado", precision = 10, scale = 2)
    private BigDecimal valorAcordado;

    @CreationTimestamp
    @Column(name = "atribuido_em", nullable = false, updatable = false)
    private LocalDateTime atribuidoEm;

    // ========== CONSTRUTOR AUXILIAR ==========

    /**
     * Construtor de conveniência para criar o relacionamento
     */
    public ProjetoFreelancer(Projeto projeto, Freelancer freelancer, String papel, BigDecimal valorAcordado) {
        this.id = new ProjetoFreelancerId(projeto.getId(), freelancer.getId());
        this.projeto = projeto;
        this.freelancer = freelancer;
        this.papel = papel;
        this.valorAcordado = valorAcordado;
    }

}

/**
 * CREATE TABLE IF NOT EXISTS projeto_freelancer (
 * projeto_id BIGINT NOT NULL REFERENCES projeto(id) ON DELETE CASCADE,
 * freelancer_id BIGINT NOT NULL REFERENCES freelancer(id) ON DELETE CASCADE,
 * papel VARCHAR(100) NOT NULL,
 * valor_acordado DECIMAL(10,2),
 * atribuido_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 * PRIMARY KEY (projeto_id, freelancer_id)
 */
