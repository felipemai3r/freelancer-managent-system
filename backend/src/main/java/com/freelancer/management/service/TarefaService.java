package com.freelancer.management.service;

import com.freelancer.management.dto.TarefaRequestDTO;
import com.freelancer.management.model.Atividade;
import com.freelancer.management.model.Freelancer;
import com.freelancer.management.model.Tarefa;
import com.freelancer.management.model.enums.StatusTarefa;
import com.freelancer.management.repository.AtividadeRepository;
import com.freelancer.management.repository.FreelancerRepository;
import com.freelancer.management.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    /**
     * Lista tarefas de uma atividade
     */
    public List<Tarefa> listarPorAtividade(Long atividadeId) {
        return tarefaRepository.findByAtividadeId(atividadeId);
    }

    /**
     * Lista tarefas de um freelancer
     */
    public List<Tarefa> listarPorFreelancer(Long freelancerId) {
        return tarefaRepository.findByFreelancerId(freelancerId);
    }

    /**
     * Busca tarefa por ID
     */
    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada: " + id));
    }

    /**
     * Cria nova tarefa
     */
    @Transactional
    public Tarefa criar(TarefaRequestDTO dto) {
        Atividade atividade = atividadeRepository.findById(dto.getAtividadeId())
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada: " + dto.getAtividadeId()));

        Freelancer freelancer = freelancerRepository.findById(dto.getFreelancerId())
                .orElseThrow(() -> new RuntimeException("Freelancer não encontrado: " + dto.getFreelancerId()));

        Tarefa tarefa = new Tarefa();
        tarefa.setAtividade(atividade);
        tarefa.setFreelancer(freelancer);
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setPrioridade(dto.getPrioridade() != null ? dto.getPrioridade() : 
                            com.freelancer.management.model.enums.Prioridade.MEDIA);
        tarefa.setPrazo(dto.getPrazo());
        tarefa.setValor(dto.getValor());
        tarefa.setStatus(dto.getStatus() != null ? dto.getStatus() : 
                        com.freelancer.management.model.enums.StatusTarefa.PENDENTE);

        return tarefaRepository.save(tarefa);
    }

    /**
     * Atualiza tarefa
     */
    @Transactional
    public Tarefa atualizar(Long id, TarefaRequestDTO dto) {
        Tarefa tarefa = buscarPorId(id);
        
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setPrioridade(dto.getPrioridade());
        tarefa.setPrazo(dto.getPrazo());
        tarefa.setValor(dto.getValor());
        
        if (dto.getStatus() != null) {
            tarefa.setStatus(dto.getStatus());
        }

        return tarefaRepository.save(tarefa);
    }

    /**
     * Atualiza status da tarefa
     */
    @Transactional
    public Tarefa atualizarStatus(Long id, StatusTarefa status) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.atualizarStatus(status);
        return tarefaRepository.save(tarefa);
    }

    /**
     * Deleta tarefa
     */
    @Transactional
    public void deletar(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada: " + id);
        }
        tarefaRepository.deleteById(id);
    }
}