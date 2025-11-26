package com.freelancer.management.dto;

import com.freelancer.management.model.enums.StatusProjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoRequestDTO {
    
    @NotNull(message = "ID da empresa é obrigatório")
    private Long empresaId;
    
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    
    private String descricao;
    private BigDecimal orcamentoTotal;
    private LocalDate dataInicio;
    private LocalDate dataFimPrevista;
    private StatusProjeto status;
}