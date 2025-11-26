package com.freelancer.management.repository;

import com.freelancer.management.model.Atividade;
import com.freelancer.management.model.enums.StatusAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    // ========== BUSCA POR PROJETO ==========
    
    /**
     * Busca atividades de um projeto ordenadas por ordem
     */
    List<Atividade> findByProjetoIdOrderByOrdemAsc(Long projetoId);

    /**
     * Busca atividades por projeto e status
     */
    List<Atividade> findByProjetoIdAndStatus(Long projetoId, StatusAtividade status);

    // ========== BUSCA POR STATUS ==========
    
    /**
     * Busca atividades por status
     */
    List<Atividade> findByStatus(StatusAtividade status);

    // ========== BUSCA COM RELACIONAMENTOS ==========
    
    /**
     * Busca atividade com tarefas carregadas
     */
    @Query("SELECT a FROM Atividade a LEFT JOIN FETCH a.tarefas WHERE a.id = :id")
    Optional<Atividade> findByIdWithTarefas(@Param("id") Long id);

    /**
     * Busca atividades de projeto com tarefas (CORRIGIDO)
     */
    @Query("SELECT DISTINCT a FROM Atividade a LEFT JOIN FETCH a.tarefas WHERE a.projeto.id = :projetoId ORDER BY a.ordem ASC")
    List<Atividade> findByProjetoIdWithTarefas(@Param("projetoId") Long projetoId);

    // ========== ORDENAÇÃO ==========
    
    /**
     * Busca atividade por projeto e ordem
     */
    Optional<Atividade> findByProjetoIdAndOrdem(Long projetoId, Integer ordem);

    /**
     * Busca maior ordem em um projeto
     */
    @Query("SELECT COALESCE(MAX(a.ordem), 0) FROM Atividade a WHERE a.projeto.id = :projetoId")
    Integer findMaxOrdemByProjetoId(@Param("projetoId") Long projetoId);

    // ========== ESTATÍSTICAS ==========
    
    /**
     * Conta atividades por projeto e status
     */
    long countByProjetoIdAndStatus(Long projetoId, StatusAtividade status);

    /**
     * Verifica se projeto tem atividades
     */
    boolean existsByProjetoId(Long projetoId);
}