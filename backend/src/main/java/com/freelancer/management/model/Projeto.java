package com.freelancer.management.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.freelancer.management.model.enums.StatusProjeto;

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

@Table(name = "projeto")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ========== RELACIONAMENTO COM EMPRESA ==========
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    // ========== DADOS DO PROJETO ==========
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "orcamento_total", precision = 12, scale = 2)
    private BigDecimal orcamentoTotal;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim_prevista")
    private LocalDate dataFimPrevista;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "status_projeto_enum" )
    private StatusProjeto status = StatusProjeto.PLANEJAMENTO;

    // ========== TIMESTAMPS AUTOMÁTICOS ==========
    @CreationTimestamp // ← Preenche automaticamente na criação
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    // Relacionamentos

    @OneToMany(mappedBy = "projeto", orphanRemoval = true)
    private List<ProjetoFreelancer> projetoFreelancers = new ArrayList<>();

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades = new ArrayList<>();
    /*
    */

    /**
     * Verifica se o projeto está ativo (não foi cancelado nem concluído)
     */
    public boolean isAtivo() {
        return this.status != StatusProjeto.CANCELADO && this.status != StatusProjeto.CONCLUIDO;
    }

    /**
     * Verifica se o projeto foi concluído
     */
    public boolean isConcluido() {
        return this.status == StatusProjeto.CONCLUIDO;
    }

    /**
     * Verifica se o projeto foi cancelado
     */
    public boolean isCancelado() {
        return this.status == StatusProjeto.CANCELADO;
    }

    /**
     * Retorna lista de freelancers do projeto
     */
    public List<Freelancer> obterFreelancers() {
        if (projetoFreelancers == null) {
            return new ArrayList<>();
        }
        return projetoFreelancers.stream()
                .map(ProjetoFreelancer::getFreelancer)
                .toList();
    }

    /**
     * Retorna quantidade de freelancers no projeto
     */
    public int totalFreelancers() {
        return projetoFreelancers != null ? projetoFreelancers.size() : 0;
    }

    /**
     * Retorna lista de atividades do projeto
     */
    public List<Atividade> obterAtividades() {
        return atividades != null ? atividades : new ArrayList<>();
    }

    /**
     * Retorna número de atividades do projeto
     */
    public int obterNumeroDeAtividades() {
        return atividades != null ? atividades.size() : 0;
    }

    /**
     * Calcula o progresso do projeto baseado nas atividades concluídas
     * 
     * @return percentual de 0 a 100
     */
    public double calcularProgresso() {
        if (atividades == null || atividades.isEmpty()) {
            return 0.0;
        }

        long atividadesConcluidas = atividades.stream()
                .filter(atividade -> atividade
                        .getStatus() == com.freelancer.management.model.enums.StatusAtividade.CONCLUIDA)
                .count();

        return (double) atividadesConcluidas / atividades.size() * 100;
    }

    /**
     * Retorna número de atividades concluídas
     */
    public int obterNumeroDeAtividadesConcluidas() {
        if (atividades == null) {
            return 0;
        }
        return (int) atividades.stream()
                .filter(atividade -> atividade
                        .getStatus() == com.freelancer.management.model.enums.StatusAtividade.CONCLUIDA)
                .count();
    }

    /**
     * Adiciona um freelancer ao projeto (helper method)
     */
    public void adicionarFreelancer(Freelancer freelancer, String papel, BigDecimal valorAcordado) {
        ProjetoFreelancer pf = new ProjetoFreelancer();
        pf.setProjeto(this);
        pf.setFreelancer(freelancer);
        pf.setPapel(papel);
        pf.setValorAcordado(valorAcordado);
        projetoFreelancers.add(pf);
    }

    /**
     * Adiciona uma atividade ao projeto (helper method)
     */
    public void adicionarAtividade(Atividade atividade) {
        atividades.add(atividade);
        atividade.setProjeto(this);
    }
}
