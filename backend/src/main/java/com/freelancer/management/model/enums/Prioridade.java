package com.freelancer.management.model.enums;

public enum Prioridade {
    BAIXA("Prioridade Baixa"),
    MEDIA("Prioridade Média"),
    ALTA("Prioridade Alta"),
    URGENTE("Prioridade Urgente");

    private final String descricao;

    Prioridade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Prioridade fromString(String prioridade) {
        if (prioridade == null || prioridade.isEmpty()) {
            throw new IllegalArgumentException("Prioridade não pode ser nulo ou vazio");
        }
        try {
            return Prioridade.valueOf(prioridade.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Prioridade inválida: " + prioridade +
                    ". Valores aceitos: BAIXA, MEDIA, ALTA, URGENTE");
        }
    }

    public int getNivel() {
        return switch (this) {
            case BAIXA -> 1;
            case MEDIA -> 2;
            case ALTA -> 3;
            case URGENTE -> 4;
        };
    }

    public boolean isUrgente() {
        return this == URGENTE;
    }

    /**
     * Verifica se a prioridade é alta ou urgente
     * @return true se for ALTA ou URGENTE
     */
    public boolean isAlta() {
        return this == ALTA || this == URGENTE;
    }
}
