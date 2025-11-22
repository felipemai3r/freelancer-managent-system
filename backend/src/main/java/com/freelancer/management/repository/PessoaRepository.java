package com.freelancer.management.repository;

import com.freelancer.management.model.Pessoa;
import com.freelancer.management.model.enums.TipoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
     
    Optional<Pessoa> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    // ========== NOVOS MÉTODOS ==========
    
    /**
     * Busca apenas pessoas ativas
     */
    List<Pessoa> findByAtivoTrue();
    
    /**
     * Busca pessoas ativas por tipo
     */
    List<Pessoa> findByAtivoTrueAndTipo(TipoUsuario tipo);
    
    /**
     * Busca pessoa ativa por email
     */
    Optional<Pessoa> findByEmailAndAtivoTrue(String email);
}
