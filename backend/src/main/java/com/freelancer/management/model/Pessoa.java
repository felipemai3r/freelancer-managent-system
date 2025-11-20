package com.freelancer.management.model;

import java.util.Date;
import java.util.UUID;

import com.freelancer.management.model.enums.TipoUsuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "pessoa")
@Entity

public class Pessoa {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    private String senha;

    private TipoUsuario tipoUsuario;

    private Date dataCriacao;

    private Date dataAtualizacao;

}
