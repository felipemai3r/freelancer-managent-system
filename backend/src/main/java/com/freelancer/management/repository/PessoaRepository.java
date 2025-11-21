package com.freelancer.management.repository;

import com.freelancer.management.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    /**
     * Busca pessoa por email
     */
    Optional<Pessoa> findByEmail(String email);
    
    /**
     * Verifica se email já existe
     */
    boolean existsByEmail(String email);
}
