package com.freelancer.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelancer.management.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {


    
}
