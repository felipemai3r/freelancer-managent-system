package com.freelancer.management.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.freelancer.management.model.enums.StatusAtividade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "atividade")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Atividade {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAtividade status;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @OneToMany(mappedBy = "atividade", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefas = new ArrayList<>();

    // Regras de negocio

    /**
     * Verifica se a atividade está pendente
     * 
     * @return true se estiver pendente, false caso contrário
     */
    public boolean estaPendente() {
        return this.status == StatusAtividade.PENDENTE;
    }

    /**
     * Verifica se a atividade está em andamento
     * 
     * @return true se estiver em andamento, false caso contrário
     */
    public boolean estaEmAndamento() {
        return this.status == StatusAtividade.EM_ANDAMENTO;
    }

    /**
     * Verifica se a atividade está concluída
     * 
     * @return true se estiver concluída, false caso contrário
     */
    public boolean estaConcluida() {
        return this.status == StatusAtividade.CONCLUIDA;
    }

    /**
     * Verifica se a atividade está cancelada
     * 
     * @return true se estiver cancelada, false caso contrário
     */
    public boolean estaCancelada() {
        return this.status == StatusAtividade.CANCELADA;
    }

    /**
     * Calcula o percentual de tarefas concluídas na atividade
     * 
     * @return percentual de tarefas concluídas (0.0 a 100.0)
     */
    public double percentualConcluido() {
        if (tarefas.isEmpty()) {
            return 0.0;
        }
        long concluidas = tarefas.stream()
                .filter(Tarefa::isConcluida)
                .count();
        return (concluidas * 100.0) / tarefas.size();
    }

    /**
     * Retorna a lista de tarefas concluídas na atividade
     * 
     * @return lista de tarefas concluídas
     */
    public List<Tarefa> getTarefasConcluidas() {
        List<Tarefa> concluidas = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            if (tarefa.isConcluida()) {
                concluidas.add(tarefa);
            }
        }
        return concluidas;
    }

    public List<Tarefa> getTarefasPendentes() {
        List<Tarefa> concluidas = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            if (tarefa.isPendente()) {
                concluidas.add(tarefa);
            }
        }
        return concluidas;
    }

    public List<Tarefa> getTarefasNaoIniciadas() {
        List<Tarefa> concluidas = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            if (tarefa.isNaoInciada()) {
                concluidas.add(tarefa);
            }
        }
        return concluidas;
    }

    /*
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
     * Retorna número de tarefas concluídas
     */
    public int obterNumeroTarefasConcluidas() {
        if (tarefas == null) {
            return 0;
        }
        return (int) tarefas.stream()
                .filter(Tarefa::isConcluida)
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
 * CREATE TABLE IF NOT EXISTS atividade (
 * id BIGSERIAL PRIMARY KEY,
 * projeto_id BIGINT NOT NULL REFERENCES projeto(id) ON DELETE CASCADE,
 * nome VARCHAR(255) NOT NULL,
 * descricao TEXT,
 * ordem INTEGER NOT NULL,
 * status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE'
 * CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA', 'CANCELADA')),
 * criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 * atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 * UNIQUE(projeto_id, ordem)
 * );
 */