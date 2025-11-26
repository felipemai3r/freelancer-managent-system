package com.freelancer.management.dto;

import com.freelancer.management.model.enums.StatusAtividade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeRequestDTO {
    
    @NotNull(message = "ID do projeto é obrigatório")
    private Long projetoId;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    private String descricao;
    
    @NotNull(message = "Ordem é obrigatória")
    private Integer ordem;
    
    private StatusAtividade status;
}