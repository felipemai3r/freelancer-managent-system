package com.freelancer.management.model.enums;

public enum StatusTarefa {
    PENDENTE("Tarefa Pendente"),
    EM_PROGRESSO("Tarefa em Progresso"),
    AGUARDANDO_ENTREGA("Tarefa Aguardando Entrega"),
    ENTREGA_RECEBIDA("Entrega da Tarefa Recebida"),
    APROVADA("Tarefa Aprovada"),
    REVISAO_NECESSARIA("Tarefa Necessita de Revisão"),
    CONCLUIDA("Tarefa Concluída"),
    CANCELADA("Tarefa Cancelada");

    private final String descricao;

    StatusTarefa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Converte uma String para o enum StatusTarefa
     * 
     * @param status String a ser convertida
     * @return Enum StatusTarefa correspondente
     */
    public static StatusTarefa fromString(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status da tarefa não pode ser nulo ou vazio");
        }
        try {
            return StatusTarefa.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status da tarefa inválido: " + status +
                    ". Valores aceitos: PENDENTE, EM_PROGRESSO, AGUARDANDO_ENTREGA, ENTREGA_RECEBIDA, APROVADA, REVISAO_NECESSARIA, CONCLUIDA, CANCELADA");
        }
    }

    /**
     * 
     * Verifica se é um status de tarefa válido
     * 
     * @param valor String a ser validada
     * @return true se for válido, false caso contrário
     */
    public static boolean isValid(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        try {
            StatusTarefa.valueOf(valor.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Verifica se o status da tarefa permite iniciar
     * 
     * @return true se for PENDENTE
     */
    public boolean podeIniciar() {
        return this == PENDENTE;
    }

    /**
     * Verifica se o status da tarefa permite envio para entrega
     * 
     * @return true se for EM_PROGRESSO
     */
    public boolean podeEnviarParaEntrega() {
        return this == EM_PROGRESSO;
    }

    /**
     * Verifica se o status da tarefa permite aprovação
     * 
     * @return true se for ENTREGA_RECEBIDA
     */
    public boolean podeAprovar() {
        return this == ENTREGA_RECEBIDA;
    }

    /**
     * Verifica se o status da tarefa permite solicitar revisão
     * 
     * @return true se for ENTREGA_RECEBIDA
     */
    public boolean podeSolicitarRevisao() {
        return this == ENTREGA_RECEBIDA;
    }

    /**
     * 
     * Verifica se o status da tarefa é finalizado
     * 
     * @return true se for CONCLUIDA ou CANCELADA
     */
    public boolean isFinalizado() {
        return this == CONCLUIDA || this == CANCELADA;
    }

    /**
     * Verifica se o status da tarefa é cancelado
     * 
     * @return true se for CANCELADA
     */
    public boolean isCancelado() {
        return this == CANCELADA;
    }

    /**
     * \Verifica se a tarefa está aguardando ação do freelancer
     * 
     * @return true se estiver aguardando ação do freelancer
     */
    public boolean aguardandoFreelancer() {
        return this == PENDENTE || this == REVISAO_NECESSARIA || this == EM_PROGRESSO;
    }

    /**
     * Verifica se a tarefa está aguardando ação da empresa
     * 
     * @return true se estiver aguardando ação da empresa
     */
    public boolean aguardandoEmpresa() {
        return this == AGUARDANDO_ENTREGA || this == ENTREGA_RECEBIDA;
    }

}
