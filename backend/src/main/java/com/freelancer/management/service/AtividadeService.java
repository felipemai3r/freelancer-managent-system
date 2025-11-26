package com.freelancer.management.service;

import com.freelancer.management.dto.AtividadeRequestDTO;
import com.freelancer.management.model.Atividade;
import com.freelancer.management.model.Projeto;
import com.freelancer.management.repository.AtividadeRepository;
import com.freelancer.management.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    /**
     * Lista atividades de um projeto
     */
    public List<Atividade> listarPorProjeto(Long projetoId) {
        return atividadeRepository.findByProjetoIdOrderByOrdemAsc(projetoId);
    }

    /**
     * Lista atividades de um projeto com tarefas
     */
    public List<Atividade> listarPorProjetoComTarefas(Long projetoId) {
        return atividadeRepository.findByProjetoIdWithTarefas(projetoId);
    }

    /**
     * Busca atividade por ID
     */
    public Atividade buscarPorId(Long id) {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada: " + id));
    }

    /**
     * Cria nova atividade
     */
    @Transactional
    public Atividade criar(AtividadeRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + dto.getProjetoId()));

        Atividade atividade = new Atividade();
        atividade.setProjeto(projeto);
        atividade.setNome(dto.getNome());
        atividade.setDescricao(dto.getDescricao());
        atividade.setOrdem(dto.getOrdem());
        atividade.setStatus(dto.getStatus() != null ? dto.getStatus() : 
                           com.freelancer.management.model.enums.StatusAtividade.PENDENTE);

        return atividadeRepository.save(atividade);
    }

    /**
     * Atualiza atividade
     */
    @Transactional
    public Atividade atualizar(Long id, AtividadeRequestDTO dto) {
        Atividade atividade = buscarPorId(id);
        
        atividade.setNome(dto.getNome());
        atividade.setDescricao(dto.getDescricao());
        atividade.setOrdem(dto.getOrdem());
        
        if (dto.getStatus() != null) {
            atividade.setStatus(dto.getStatus());
        }

        return atividadeRepository.save(atividade);
    }

    /**
     * Deleta atividade
     */
    @Transactional
    public void deletar(Long id) {
        if (!atividadeRepository.existsById(id)) {
            throw new RuntimeException("Atividade não encontrada: " + id);
        }
        atividadeRepository.deleteById(id);
    }

    /**
     * Move atividade para cima
     */
    @Transactional
    public void moverParaCima(Long id) {
        Atividade atividade = buscarPorId(id);
        if (atividade.getOrdem() > 1) {
            atividade.moverParaCima();
            atividadeRepository.save(atividade);
        }
    }

    /**
     * Move atividade para baixo
     */
    @Transactional
    public void moverParaBaixo(Long id) {
        Atividade atividade = buscarPorId(id);
        atividade.moverParaBaixo();
        atividadeRepository.save(atividade);
    }
}