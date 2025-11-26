package com.freelancer.management.controller;

import com.freelancer.management.dto.ProjetoFreelancerRequestDTO;
import com.freelancer.management.model.ProjetoFreelancer;
import com.freelancer.management.service.ProjetoFreelancerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar Freelancers em Projetos
 * 
 * Endpoints:
 * GET    /api/projetos/{projetoId}/freelancers           - Lista freelancers do projeto
 * POST   /api/projetos/{projetoId}/freelancers           - Adiciona freelancer ao projeto
 * DELETE /api/projetos/{projetoId}/freelancers/{freelancerId} - Remove freelancer do projeto
 * GET    /api/freelancers/{freelancerId}/projetos        - Lista projetos do freelancer
 * 
 * @author Felipe Maier
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:80"})
public class ProjetoFreelancerController {

    @Autowired
    private ProjetoFreelancerService projetoFreelancerService;

    /**
     * GET /api/projetos/{projetoId}/freelancers
     * Lista freelancers atribuídos ao projeto
     */
    @GetMapping("/projetos/{projetoId}/freelancers")
    public ResponseEntity<List<ProjetoFreelancer>> listarFreelancersPorProjeto(
            @PathVariable Long projetoId) {
        List<ProjetoFreelancer> freelancers = projetoFreelancerService.listarFreelancersPorProjeto(projetoId);
        return ResponseEntity.ok(freelancers);
    }

    /**
     * POST /api/projetos/{projetoId}/freelancers
     * Adiciona freelancer ao projeto
     * Body: { "freelancerId": 1, "papel": "Designer", "valorAcordado": 5000.00 }
     */
    @PostMapping("/projetos/{projetoId}/freelancers")
    public ResponseEntity<ProjetoFreelancer> adicionarFreelancerAoProjeto(
            @PathVariable Long projetoId,
            @Valid @RequestBody ProjetoFreelancerRequestDTO dto) {
        try {
            ProjetoFreelancer criado = projetoFreelancerService.adicionarFreelancerAoProjeto(projetoId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/projetos/{projetoId}/freelancers/{freelancerId}
     * Remove freelancer do projeto
     */
    @DeleteMapping("/projetos/{projetoId}/freelancers/{freelancerId}")
    public ResponseEntity<Void> removerFreelancerDoProjeto(
            @PathVariable Long projetoId,
            @PathVariable Long freelancerId) {
        try {
            projetoFreelancerService.removerFreelancerDoProjeto(projetoId, freelancerId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/freelancers/{freelancerId}/projetos
     * Lista projetos do freelancer
     */
    @GetMapping("/freelancers/{freelancerId}/projetos")
    public ResponseEntity<List<ProjetoFreelancer>> listarProjetosPorFreelancer(
            @PathVariable Long freelancerId) {
        List<ProjetoFreelancer> projetos = projetoFreelancerService.listarProjetosPorFreelancer(freelancerId);
        return ResponseEntity.ok(projetos);
    }
}