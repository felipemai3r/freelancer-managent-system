package com.freelancer.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoFreelancerRequestDTO {
    
    @NotNull(message = "ID do freelancer é obrigatório")
    private Long freelancerId;
    
    @NotBlank(message = "Papel é obrigatório")
    private String papel;
    
    private BigDecimal valorAcordado;
}