package com.freelancer.management.dto;

import com.freelancer.management.model.Projeto;
import com.freelancer.management.model.enums.StatusProjeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoResponseDTO {
    
    private Long id;
    private Long empresaId;
    private String nomeEmpresa;
    private String titulo;
    private String descricao;
    private BigDecimal orcamentoTotal;
    private LocalDate dataInicio;
    private LocalDate dataFimPrevista;
    private StatusProjeto status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private Integer totalAtividades;
    private Integer totalFreelancers;
    private Double progresso;

    // Construtor a partir da entidade
    public ProjetoResponseDTO(Projeto projeto) {
        this.id = projeto.getId();
        this.empresaId = projeto.getEmpresa().getId();
        this.nomeEmpresa = projeto.getEmpresa().getNomeEmpresa();
        this.titulo = projeto.getTitulo();
        this.descricao = projeto.getDescricao();
        this.orcamentoTotal = projeto.getOrcamentoTotal();
        this.dataInicio = projeto.getDataInicio();
        this.dataFimPrevista = projeto.getDataFimPrevista();
        this.status = projeto.getStatus();
        this.criadoEm = projeto.getCriadoEm();
        this.atualizadoEm = projeto.getAtualizadoEm();
        this.totalAtividades = projeto.obterNumeroDeAtividades();
        this.totalFreelancers = projeto.totalFreelancers();
        this.progresso = projeto.calcularProgresso();
    }
}