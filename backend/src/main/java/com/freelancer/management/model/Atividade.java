package com.freelancer.management.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.freelancer.management.model.enums.StatusAtividade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade Atividade - Representa uma atividade dentro de um projeto
 * 
 * Relacionamentos:
 * - ManyToOne com Projeto (uma atividade pertence a um projeto)
 * - OneToMany com Tarefa (uma atividade tem várias tarefas)
 * 
 * Nota: Atividades são organizadas por 'ordem' dentro do projeto
 * 
 * @author Felipe Maier
 * @version 1.0
 */
@Table(name = "atividade")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Atividade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ========== RELACIONAMENTO COM PROJETO ==========
    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    // ========== DADOS DA ATIVIDADE ==========
    
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    @Enumerated(EnumType.STRING) // ← CORRIGIDO: Adiciona annotation para enum
    @Column(name = "status", nullable = false, length = 20)
    private StatusAtividade status = StatusAtividade.PENDENTE;

    // ========== TIMESTAMPS AUTOMÁTICOS ==========
    
    @CreationTimestamp // ← CORRIGIDO: Usa annotation do Hibernate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm; // ← CORRIGIDO: Mudado de Data para LocalDateTime

    @UpdateTimestamp // ← CORRIGIDO: Atualiza automaticamente
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm; // ← CORRIGIDO: Mudado de Data para LocalDateTime

    // ========== RELACIONAMENTO COM TAREFAS ==========
    
    /**
     * CORRIGIDO: Adiciona relacionamento OneToMany com Tarefa
     * CascadeType.ALL: quando atividade é deletada, suas tarefas também são
     */
    @OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefas = new ArrayList<>();

    // ========== MÉTODOS DE NEGÓCIO ==========

    /**
     * Verifica se a atividade está concluída
     */
    public boolean isConcluida() {
        return this.status == StatusAtividade.CONCLUIDA;
    }

    /**
     * Verifica se a atividade está pendente
     */
    public boolean isPendente() {
        return this.status == StatusAtividade.PENDENTE;
    }

    /**
     * Verifica se a atividade está em andamento
     */
    public boolean isEmAndamento() {
        return this.status == StatusAtividade.EM_ANDAMENTO;
    }

    /**
     * Verifica se a atividade foi cancelada
     */
    public boolean isCancelada() {
        return this.status == StatusAtividade.CANCELADA;
    }

    /**
     * Retorna lista de tarefas da atividade
     */
    public List<Tarefa> obterTarefas() {
        return tarefas != null ? tarefas : new ArrayList<>();
    }

    /**
     * Retorna número de tarefas na atividade
     */
    public int totalTarefas() {
        return tarefas != null ? tarefas.size() : 0;
    }

    /**
     * Calcula o progresso da atividade baseado nas tarefas concluídas
     * 
     * @return percentual de 0 a 100
     */
    public double calcularProgresso() {
        if (tarefas == null || tarefas.isEmpty()) {
            return 0.0;
        }
        
        long tarefasConcluidas = tarefas.stream()
                .filter(tarefa -> tarefa.getStatus() == com.freelancer.management.model.enums.StatusTarefa.CONCLUIDA)
                .count();
        
        return (double) tarefasConcluidas / tarefas.size() * 100;
    }

    /**
     * Retorna número de tarefas concluídas
     */
    public int obterNumeroTarefasConcluidas() {
        if (tarefas == null) {
            return 0;
        }
        return (int) tarefas.stream()
                .filter(tarefa -> tarefa.getStatus() == com.freelancer.management.model.enums.StatusTarefa.CONCLUIDA)
                .count();
    }

    /**
     * Adiciona uma tarefa à atividade (helper method)
     */
    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        tarefa.setAtividade(this);
    }

    /**
     * Move a atividade uma posição para cima
     */
    public void moverParaCima() {
        if (this.ordem > 1) {
            this.ordem--;
        }
    }

    /**
     * Move a atividade uma posição para baixo
     */
    public void moverParaBaixo() {
        this.ordem++;
    }
}

/*
 * SQL DE REFERÊNCIA:
 * 
 * CREATE TABLE IF NOT EXISTS atividade (
 *     id BIGSERIAL PRIMARY KEY,
 *     projeto_id BIGINT NOT NULL REFERENCES projeto(id) ON DELETE CASCADE,
 *     nome VARCHAR(255) NOT NULL,
 *     descricao TEXT,
 *     ordem INTEGER NOT NULL,
 *     status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE'
 *         CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA', 'CANCELADA')),
 *     criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     UNIQUE(projeto_id, ordem)
 * );
 */