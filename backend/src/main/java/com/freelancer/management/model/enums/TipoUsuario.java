package com.freelancer.management.model.enums;

public enum TipoUsuario {
    ADMIN("Administrador do Sistema"),
    EMPRESA("Empresa Contratante"),
    FREELANCER("Usuário Freelance");

    private final String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoUsuario fromString(String tipo){
        if(tipo == null || tipo.isEmpty()){
            throw new IllegalArgumentException("Tipo de usuário não pode ser nulo ou vazio");
        }
        try{
            return TipoUsuario.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException( "Tipo de usuário inválido: " + tipo + 
                                                 ". Valores aceitos: ADMIN, EMPRESA, FREELANCER");
        }
    }
    /**
     * Verifica se é um tipo de usuário válido
     * @param valor String a ser validada
     * @return true se for válido, false caso contrário
     */
    public static boolean isValid(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        try {
            TipoUsuario.valueOf(valor.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Verifica se o tipo é ADMIN
     * @return true se for ADMIN
     */
    public boolean isAdmin() {
        return this == ADMIN;
    }

    /**
     * Verifica se o tipo é EMPRESA
     * @return true se for EMPRESA
     */
    public boolean isEmpresa() {
        return this == EMPRESA;
    }

    /**
     * Verifica se o tipo é FREELANCER
     * @return true se for FREELANCER
     */
    public boolean isFreelancer() {
        return this == FREELANCER;
    }

}
