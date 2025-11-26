package com.freelancer.management.service;

import com.freelancer.management.dto.ProjetoRequestDTO;
import com.freelancer.management.dto.ProjetoResponseDTO;
import com.freelancer.management.model.Empresa;
import com.freelancer.management.model.Projeto;
import com.freelancer.management.repository.EmpresaRepository;
import com.freelancer.management.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Lista todos os projetos
     */
    public List<ProjetoResponseDTO> listarTodos() {
        return projetoRepository.findAll().stream()
                .map(ProjetoResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca projeto por ID
     */
    public ProjetoResponseDTO buscarPorId(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + id));
        return new ProjetoResponseDTO(projeto);
    }

    /**
     * Busca projeto por ID com atividades
     */
    public ProjetoResponseDTO buscarPorIdComAtividades(Long id) {
        Projeto projeto = projetoRepository.findByIdWithAtividades(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + id));
        return new ProjetoResponseDTO(projeto);
    }

    /**
     * Cria novo projeto
     */
    @Transactional
    public ProjetoResponseDTO criar(ProjetoRequestDTO dto) {
        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada: " + dto.getEmpresaId()));

        Projeto projeto = new Projeto();
        projeto.setEmpresa(empresa);
        projeto.setTitulo(dto.getTitulo());
        projeto.setDescricao(dto.getDescricao());
        projeto.setOrcamentoTotal(dto.getOrcamentoTotal());
        projeto.setDataInicio(dto.getDataInicio());
        projeto.setDataFimPrevista(dto.getDataFimPrevista());
        projeto.setStatus(dto.getStatus() != null ? dto.getStatus() : 
                         com.freelancer.management.model.enums.StatusProjeto.PLANEJAMENTO);

        Projeto salvo = projetoRepository.save(projeto);
        return new ProjetoResponseDTO(salvo);
    }

    /**
     * Atualiza projeto
     */
    @Transactional
    public ProjetoResponseDTO atualizar(Long id, ProjetoRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + id));

        projeto.setTitulo(dto.getTitulo());
        projeto.setDescricao(dto.getDescricao());
        projeto.setOrcamentoTotal(dto.getOrcamentoTotal());
        projeto.setDataInicio(dto.getDataInicio());
        projeto.setDataFimPrevista(dto.getDataFimPrevista());
        
        if (dto.getStatus() != null) {
            projeto.setStatus(dto.getStatus());
        }

        Projeto atualizado = projetoRepository.save(projeto);
        return new ProjetoResponseDTO(atualizado);
    }

    /**
     * Deleta projeto
     */
    @Transactional
    public void deletar(Long id) {
        if (!projetoRepository.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado: " + id);
        }
        projetoRepository.deleteById(id);
    }

    /**
     * Lista projetos de uma empresa
     */
    public List<ProjetoResponseDTO> listarPorEmpresa(Long empresaId) {
        return projetoRepository.findByEmpresaId(empresaId).stream()
                .map(ProjetoResponseDTO::new)
                .collect(Collectors.toList());
    }
}