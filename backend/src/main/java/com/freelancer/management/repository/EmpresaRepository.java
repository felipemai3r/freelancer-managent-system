package com.freelancer.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.freelancer.management.model.Empresa;
import com.freelancer.management.model.Pessoa;

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

  /**
   * Busca empresas ativas
   * IMPORTANTE: ativo está em Pessoa, não em Empresa!
   */
  @Query("SELECT e FROM Empresa e WHERE e.pessoa.ativo = true")
  List<Empresa> findEmpresasAtivas();

}
