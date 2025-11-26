package com.freelancer.management.dto;

import com.freelancer.management.model.enums.Prioridade;
import com.freelancer.management.model.enums.StatusTarefa;
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
public class TarefaRequestDTO {
    
    @NotNull(message = "ID da atividade é obrigatório")
    private Long atividadeId;
    
    @NotNull(message = "ID do freelancer é obrigatório")
    private Long freelancerId;
    
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    
    private String descricao;
    private Prioridade prioridade;
    private LocalDate prazo;
    private BigDecimal valor;
    private StatusTarefa status;
}