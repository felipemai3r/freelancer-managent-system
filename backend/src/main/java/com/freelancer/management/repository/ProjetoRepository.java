package com.freelancer.management.repository;

import com.freelancer.management.model.Projeto;
import com.freelancer.management.model.enums.StatusProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    
    // Buscar projetos por empresa
    List<Projeto> findByEmpresaId(Long empresaId);
    
    // Buscar por status
    List<Projeto> findByStatus(StatusProjeto status);
    
    // Buscar projetos ativos de uma empresa
    @Query("SELECT p FROM Projeto p WHERE p.empresa.id = :empresaId AND p.status IN ('PLANEJAMENTO', 'EM_ANDAMENTO')")
    List<Projeto> findProjetosAtivos(@Param("empresaId") Long empresaId);
    
    // Buscar projeto com suas atividades (evita LazyInitializationException)
    @Query("SELECT p FROM Projeto p LEFT JOIN FETCH p.atividades WHERE p.id = :id")
    Optional<Projeto> findByIdWithAtividades(@Param("id") Long id);
    
    // Buscar projeto com freelancers
    @Query("SELECT p FROM Projeto p LEFT JOIN FETCH p.projetoFreelancers WHERE p.id = :id")
    Optional<Projeto> findByIdWithFreelancers(@Param("id") Long id);
}