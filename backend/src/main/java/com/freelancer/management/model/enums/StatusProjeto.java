package com.freelancer.management.model.enums;

public enum StatusProjeto {
    PLANEJAMENTO("Projeto em Planejamento"),
    EM_ANDAMENTO("Projeto em Andamento"),
    CONCLUIDO("Projeto Concluído"),
    CANCELADO("Projeto Cancelado");

    private final String descricao;

    /**
     * Construtor do enum StatusProjeto
     * 
     * @param descricao Descrição do status do projeto
     */
    StatusProjeto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Converte uma String para o enum StatusProjeto
     * 
     * @param status String a ser convertida
     * @return Enum StatusProjeto correspondente
     */
    public static StatusProjeto fromString(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status do projeto não pode ser nulo ou vazio");
        }
        try {
            return StatusProjeto.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status do projeto inválido: " + status +
                    ". Valores aceitos: PLANEJAMENTO, EM_ANDAMENTO, CONCLUIDO, CANCELADO");
        }
    }

    public static boolean isValid(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        try {
            StatusProjeto.valueOf(valor.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Verifica se o status do projeto é finalizado
     * 
     * @return true se for CONCLUIDO ou CANCELADO
     */
    public boolean isFinalizado() {
        return this == CONCLUIDO || this == CANCELADO;
    }

    /**
     * Verifica se o status do projeto é cancelado
     * 
     * @return true se for CANCELADO
     */
    public boolean isCancelado() {
        return this == CANCELADO;
    }

    /**
     * Verifica se o status do projeto é em andamento
     * 
     * @return true se for EM_ANDAMENTO
     */
    public boolean isEmAndamento() {
        return this == EM_ANDAMENTO;
    }

    /**
     * Verifica se o status do projeto é em planejamento
     * 
     * @return true se for PLANEJAMENTO
     */
    public boolean isPlanejamento() {
        return this == PLANEJAMENTO;
    }

    /**
     * Verifica se o status do projeto é ativo (em planejamento ou em andamento)
     * 
     * @return true se for PLANEJAMENTO ou EM_ANDAMENTO
     */
    public boolean isAtivo() {
        return this == PLANEJAMENTO || this == EM_ANDAMENTO;
    }

    /**
     * Verifica se o projeto pode ser editado
     * 
     * @return true se for PLANEJAMENTO ou EM_ANDAMENTO
     */
    public boolean podeEditar() {
        return this == PLANEJAMENTO || this == EM_ANDAMENTO;
    }
}
