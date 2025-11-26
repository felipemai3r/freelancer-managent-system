package com.freelancer.management.repository;

import com.freelancer.management.model.ProjetoFreelancer;
import com.freelancer.management.model.ProjetoFreelancerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoFreelancerRepository extends JpaRepository<ProjetoFreelancer, ProjetoFreelancerId> {

    // ========== BUSCA POR PROJETO ==========
    
    /**
     * Busca freelancers de um projeto
     */
    @Query("SELECT pf FROM ProjetoFreelancer pf WHERE pf.projeto.id = :projetoId")
    List<ProjetoFreelancer> findByProjetoId(@Param("projetoId") Long projetoId);

    /**
     * Busca freelancers de projeto com dados carregados (CORRIGIDO)
     */
    @Query("SELECT pf FROM ProjetoFreelancer pf JOIN FETCH pf.freelancer WHERE pf.projeto.id = :projetoId")
    List<ProjetoFreelancer> findByProjeto_IdWithFreelancer(@Param("projetoId") Long projetoId);

    // ========== BUSCA POR FREELANCER ==========
    
    /**
     * Busca projetos de um freelancer
     */
    @Query("SELECT pf FROM ProjetoFreelancer pf WHERE pf.freelancer.id = :freelancerId")
    List<ProjetoFreelancer> findByFreelancerId(@Param("freelancerId") Long freelancerId);

    /**
     * Busca projetos de freelancer com dados carregados (CORRIGIDO)
     */
    @Query("SELECT pf FROM ProjetoFreelancer pf JOIN FETCH pf.projeto WHERE pf.freelancer.id = :freelancerId")
    List<ProjetoFreelancer> findByFreelancer_IdWithProjeto(@Param("freelancerId") Long freelancerId);

    // ========== VERIFICAÇÕES ==========
    
    /**
     * Verifica se freelancer está em projeto (CORRIGIDO)
     */
    @Query("SELECT CASE WHEN COUNT(pf) > 0 THEN true ELSE false END FROM ProjetoFreelancer pf WHERE pf.projeto.id = :projetoId AND pf.freelancer.id = :freelancerId")
    boolean existsByProjeto_IdAndFreelancer_Id(@Param("projetoId") Long projetoId, @Param("freelancerId") Long freelancerId);

    /**
     * Busca relacionamento específico
     */
    @Query("SELECT pf FROM ProjetoFreelancer pf WHERE pf.projeto.id = :projetoId AND pf.freelancer.id = :freelancerId")
    Optional<ProjetoFreelancer> findByProjetoIdAndFreelancerId(@Param("projetoId") Long projetoId, @Param("freelancerId") Long freelancerId);

    // ========== BUSCA POR PAPEL ==========
    
    /**
     * Busca freelancers por projeto e papel
     */
    @Query("SELECT pf FROM ProjetoFreelancer pf WHERE pf.projeto.id = :projetoId AND pf.papel = :papel")
    List<ProjetoFreelancer> findByProjetoIdAndPapel(@Param("projetoId") Long projetoId, @Param("papel") String papel);

    // ========== ESTATÍSTICAS ==========
    
    /**
     * Conta freelancers em projeto
     */
    @Query("SELECT COUNT(pf) FROM ProjetoFreelancer pf WHERE pf.projeto.id = :projetoId")
    long countByProjetoId(@Param("projetoId") Long projetoId);

    /**
     * Conta projetos de freelancer
     */
    @Query("SELECT COUNT(pf) FROM ProjetoFreelancer pf WHERE pf.freelancer.id = :freelancerId")
    long countByFreelancerId(@Param("freelancerId") Long freelancerId);
}