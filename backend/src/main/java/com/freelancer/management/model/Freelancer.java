package com.freelancer.management.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "freelancer")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Freelancer {
    @Id
    private Long id;

    @OneToOne // Relação um-para-um com Pessoa
    @MapsId // Usa o mesmo ID de Pessoa
    @JoinColumn(name = "id") // Chave estrangeira para Pessoa
    private Pessoa pessoa;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(name = "cpf_cnpj", nullable = false, unique = true, length = 14)
    private String cpfCnpj;

    @Column(name = "is_pj")
    private Boolean isPj;

    @Column(name = "habilidades", columnDefinition = "TEXT")
    private String habilidades;
    
    @Column(name = "valor_hora")
    private BigDecimal valorHora;
    
    @Column(name = "portfolio_url", length = 500)
    private String portfolioUrl;


    public String getEmail() {
        return pessoa != null ? pessoa.getEmail() : null;
    }
    
    public boolean cpfCnpjValido() {
        if (cpfCnpj == null) return false;
        String numeros = cpfCnpj.replaceAll("\\D", "");
        return numeros.length() == 11 || numeros.length() == 14;
    }

    /**
     * Retorna CPF ou CNPJ formatado
     */
    public String getCpfCnpjFormatado() {
        if (cpfCnpj == null) return cpfCnpj;
        String numeros = cpfCnpj.replaceAll("\\D", "");
        if (numeros.length() == 11) {
            return String.format("%s.%s.%s-%s",
                numeros.substring(0, 3),
                numeros.substring(3, 6),
                numeros.substring(6, 9),
                numeros.substring(9, 11)
            );
        } else if (numeros.length() == 14) {
            return String.format("%s.%s.%s/%s-%s",
                numeros.substring(0, 2),
                numeros.substring(2, 5),
                numeros.substring(5, 8),
                numeros.substring(8, 12),
                numeros.substring(12, 14)
            );
        } else {
            return cpfCnpj;
        }
    }

    /**
     * Verifica se é Pessoa Jurídica
     * @return boolean se for PJ
     */
    public boolean isPessoaJuridica() {
        return Boolean.TRUE.equals(this.isPj);
    }

    public boolean isPessoaFisica() {
        return Boolean.FALSE.equals(this.isPj);
    }


}
