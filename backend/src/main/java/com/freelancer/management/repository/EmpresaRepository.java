package com.freelancer.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.freelancer.management.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    /**
     * Busca empresa por CNPJ
     */
    Optional<Empresa> findByCnpj(String cnpj);
    
    /**
     * Verifica se CNPJ já existe
     */
    boolean existsByCnpj(String cnpj);


    
}
