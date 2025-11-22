package com.freelancer.management.model;

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

@Table(name = "empresa")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    private Long id;

    @OneToOne // Relação um-para-um com Pessoa
    @MapsId // Usa o mesmo ID de Pessoa
    @JoinColumn(name = "id") // Chave estrangeira para Pessoa
    private Pessoa pessoa;

    @Column(name = "nome_empresa", nullable = false)
    private String nomeEmpresa;
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;
    @Column(name = "telefone", length = 15)
    private String telefone;
    @Column(name = "endereco", columnDefinition = "TEXT")
    private String endereco;

    public String getEmail() {
        return this.pessoa.getEmail();
    }

    public boolean cnpjValido() {
        if (cnpj == null)
            return false;
        String cnpjNumeros = cnpj.replaceAll("\\D", "");
        return cnpjNumeros.length() == 14;
    }

    /**
     * Retorna CNPJ formatado
     */
    public String getCnpjFormatado() {
        if (cnpj == null || cnpj.length() != 14)
            return cnpj;
        return String.format("%s.%s.%s/%s-%s",
                cnpj.substring(0, 2),
                cnpj.substring(2, 5),
                cnpj.substring(5, 8),
                cnpj.substring(8, 12),
                cnpj.substring(12, 14));
    }

    public boolean isAtivo() {
        return pessoa != null && pessoa.isAtivo();
    }

    public void desativar() {
        if (pessoa != null) {
            pessoa.desativar();
        }
    }

    public void ativar() {
        if (pessoa != null) {
            pessoa.ativar();
        }
    }
}
