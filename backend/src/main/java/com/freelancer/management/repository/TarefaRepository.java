package com.freelancer.management.repository;

import com.freelancer.management.model.Tarefa;
import com.freelancer.management.model.enums.StatusTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    // Buscar tarefas de uma atividade
    List<Tarefa> findByAtividadeId(Long atividadeId);
    
    // Buscar tarefas de um freelancer
    List<Tarefa> findByFreelancerId(Long freelancerId);
    
    // Buscar tarefas por status
    List<Tarefa> findByFreelancerIdAndStatus(Long freelancerId, StatusTarefa status);
    
    // Buscar tarefas com prazo próximo
    @Query("SELECT t FROM Tarefa t WHERE t.freelancer.id = :freelancerId AND t.prazo BETWEEN :hoje AND :dataLimite AND t.status <> 'CONCLUIDA'")
    List<Tarefa> findTarefasComPrazoProximo(Long freelancerId, LocalDate hoje, LocalDate dataLimite);
    
    // Buscar tarefas atrasadas
    @Query("SELECT t FROM Tarefa t WHERE t.freelancer.id = :freelancerId AND t.prazo < :hoje AND t.status <> 'CONCLUIDA'")
    List<Tarefa> findTarefasAtrasadas(Long freelancerId, LocalDate hoje);
}