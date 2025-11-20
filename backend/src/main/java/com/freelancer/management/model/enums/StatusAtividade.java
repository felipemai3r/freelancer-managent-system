package com.freelancer.management.model.enums;

public enum StatusAtividade {
    PENDENTE("Atividade Pendente"),
    EM_ANDAMENTO("Atividade em Andamento"),
    CONCLUIDA("Atividade Concluída"),
    CANCELADA("Atividade Cancelada");

    private final String descricao;

    StatusAtividade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Converte uma String para o enum StatusAtividade
     * 
     * @param atividade String a ser convertida
     * @return Enum StatusAtividade correspondente
     */
    public static StatusAtividade fromString(String atividade) {
        if (atividade == null || atividade.isEmpty()) {
            throw new IllegalArgumentException("Status da atividade não pode ser nulo ou vazio");
        }
        try {
            return StatusAtividade.valueOf(atividade.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status da atividade inválido: " + atividade +
                    ". Valores aceitos: PENDENTE, EM_ANDAMENTO, CONCLUIDA, CANCELADA");
        }
    }

    /**
     * Verifica se é um status de atividade válido
     * 
     * @param valor String a ser validada
     * @return true se for válido, false caso contrário
     */
    public static boolean isValid(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        try {
            StatusAtividade.valueOf(valor.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Verifica se o status da atividade é finalizado
     * 
     * @return true se for CONCLUIDA ou CANCELADA
     */
    public boolean isFinalizado() {
        return this == CONCLUIDA || this == CANCELADA;
    }

    /**
     * Verifica se a atividade está concluída
     * 
     * @return true se estiver concluída
     */
    public boolean isConcluida() {
        return this == CONCLUIDA;
    }

    /**
     * Verifica se a atividade está pendente
     * 
     * @return true se estiver pendente
     */
    public boolean isPendente() {
        return this == PENDENTE;
    }

    /**
     * Verifica se a atividade está em andamento
     * 
     * @return true se estiver em andamento
     */
    public boolean isEmAndamento() {
        return this == EM_ANDAMENTO;
    }
}
