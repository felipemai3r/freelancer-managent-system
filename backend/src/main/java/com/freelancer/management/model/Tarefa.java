package com.freelancer.management.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.freelancer.management.model.enums.Prioridade;
import com.freelancer.management.model.enums.StatusTarefa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tarefa")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    private Freelancer freelancer;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false, length = 20)
    private Prioridade prioridade = Prioridade.MEDIA;

    @Column(name = "prazo")
    private LocalDate prazo;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusTarefa status = StatusTarefa.PENDENTE;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp // ← TROCAR AQUI!
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    // Metodos de negocio

    public void atualizarStatus(StatusTarefa novoStatus) {
        // Regras de transição de status podem ser implementadas aqui
        this.status = novoStatus;
    }

    public boolean isAtrasada() {
        if (this.prazo == null) {
            return false;
        }
        return LocalDate.now().isAfter(this.prazo) &&
                (this.status == StatusTarefa.PENDENTE || this.status == StatusTarefa.EM_PROGRESSO);
    }

    public boolean isConcluida() {
        return this.status == StatusTarefa.CONCLUIDA;
    }

    public boolean isPendente() {
        return this.status == StatusTarefa.PENDENTE || this.status == StatusTarefa.EM_PROGRESSO;
    }

    public boolean isNaoInciada() {
        return this.status == StatusTarefa.PENDENTE;
    }

    public boolean isEmProgresso() {
        return this.status == StatusTarefa.EM_PROGRESSO;
    }

    public boolean isCancelada() {
        return this.status == StatusTarefa.CANCELADA;
    }

}

/**
 * id BIGSERIAL PRIMARY KEY,
 * atividade_id BIGINT NOT NULL REFERENCES atividade(id) ON DELETE CASCADE,
 * freelancer_id BIGINT NOT NULL REFERENCES freelancer(id) ON DELETE RESTRICT,
 * titulo VARCHAR(255) NOT NULL,
 * descricao TEXT,
 * prioridade VARCHAR(20) NOT NULL DEFAULT 'MEDIA'
 * CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA', 'URGENTE')),
 * prazo DATE,
 * valor DECIMAL(10,2),
 * status VARCHAR(30) NOT NULL DEFAULT 'PENDENTE'
 * CHECK (status IN ('PENDENTE', 'EM_PROGRESSO', 'AGUARDANDO_ENTREGA',
 * 'ENTREGA_RECEBIDA', 'APROVADA', 'REVISAO_NECESSARIA',
 * 'CONCLUIDA', 'CANCELADA')),
 * criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 * atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 * );
 */