package com.freelancer.management.service;

import com.freelancer.management.dto.ProjetoFreelancerRequestDTO;
import com.freelancer.management.model.Freelancer;
import com.freelancer.management.model.Projeto;
import com.freelancer.management.model.ProjetoFreelancer;
import com.freelancer.management.model.ProjetoFreelancerId;
import com.freelancer.management.repository.FreelancerRepository;
import com.freelancer.management.repository.ProjetoFreelancerRepository;
import com.freelancer.management.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjetoFreelancerService {

    @Autowired
    private ProjetoFreelancerRepository projetoFreelancerRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    /**
     * Lista freelancers de um projeto
     */
    public List<ProjetoFreelancer> listarFreelancersPorProjeto(Long projetoId) {
        return projetoFreelancerRepository.findByProjeto_IdWithFreelancer(projetoId);
    }

    /**
     * Lista projetos de um freelancer
     */
    public List<ProjetoFreelancer> listarProjetosPorFreelancer(Long freelancerId) {
        return projetoFreelancerRepository.findByFreelancer_IdWithProjeto(freelancerId);
    }

    /**
     * Adiciona freelancer ao projeto
     */
    @Transactional
    public ProjetoFreelancer adicionarFreelancerAoProjeto(Long projetoId, ProjetoFreelancerRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + projetoId));

        Freelancer freelancer = freelancerRepository.findById(dto.getFreelancerId())
                .orElseThrow(() -> new RuntimeException("Freelancer não encontrado: " + dto.getFreelancerId()));

        // Verifica se já existe
        if (projetoFreelancerRepository.existsByProjeto_IdAndFreelancer_Id(projetoId, dto.getFreelancerId())) {
            throw new RuntimeException("Freelancer já está atribuído a este projeto");
        }

        ProjetoFreelancer pf = new ProjetoFreelancer(projeto, freelancer, dto.getPapel(), dto.getValorAcordado());
        return projetoFreelancerRepository.save(pf);
    }

    /**
     * Remove freelancer do projeto
     */
    @Transactional
    public void removerFreelancerDoProjeto(Long projetoId, Long freelancerId) {
        ProjetoFreelancerId id = new ProjetoFreelancerId(projetoId, freelancerId);
        
        if (!projetoFreelancerRepository.existsById(id)) {
            throw new RuntimeException("Relacionamento não encontrado");
        }
        
        projetoFreelancerRepository.deleteById(id);
    }
}