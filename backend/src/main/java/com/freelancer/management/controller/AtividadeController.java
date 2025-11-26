package com.freelancer.management.controller;

import com.freelancer.management.dto.AtividadeRequestDTO;
import com.freelancer.management.model.Atividade;
import com.freelancer.management.service.AtividadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar Atividades
 * 
 * Endpoints:
 * GET    /api/projetos/{projetoId}/atividades  - Lista atividades do projeto
 * POST   /api/projetos/{projetoId}/atividades  - Cria nova atividade
 * GET    /api/atividades/{id}                  - Busca atividade por ID
 * PUT    /api/atividades/{id}                  - Atualiza atividade
 * DELETE /api/atividades/{id}                  - Deleta atividade
 * POST   /api/atividades/{id}/mover-cima       - Move atividade para cima
 * POST   /api/atividades/{id}/mover-baixo      - Move atividade para baixo
 * 
 * @author Felipe Maier
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:80"})
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    /**
     * GET /api/projetos/{projetoId}/atividades
     * Lista atividades de um projeto (com tarefas)
     */
    @GetMapping("/projetos/{projetoId}/atividades")
    public ResponseEntity<List<Atividade>> listarPorProjeto(
            @PathVariable Long projetoId,
            @RequestParam(required = false, defaultValue = "false") boolean comTarefas) {
        
        List<Atividade> atividades;
        if (comTarefas) {
            atividades = atividadeService.listarPorProjetoComTarefas(projetoId);
        } else {
            atividades = atividadeService.listarPorProjeto(projetoId);
        }
        
        return ResponseEntity.ok(atividades);
    }

    /**
     * POST /api/projetos/{projetoId}/atividades
     * Cria nova atividade no projeto
     */
    @PostMapping("/projetos/{projetoId}/atividades")
    public ResponseEntity<Atividade> criar(
            @PathVariable Long projetoId,
            @Valid @RequestBody AtividadeRequestDTO dto) {
        try {
            // Garante que o projetoId do path é usado
            dto.setProjetoId(projetoId);
            Atividade criada = atividadeService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/atividades/{id}
     * Busca atividade por ID
     */
    @GetMapping("/atividades/{id}")
    public ResponseEntity<Atividade> buscarPorId(@PathVariable Long id) {
        try {
            Atividade atividade = atividadeService.buscarPorId(id);
            return ResponseEntity.ok(atividade);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT /api/atividades/{id}
     * Atualiza atividade existente
     */
    @PutMapping("/atividades/{id}")
    public ResponseEntity<Atividade> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtividadeRequestDTO dto) {
        try {
            Atividade atualizada = atividadeService.atualizar(id, dto);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/atividades/{id}
     * Deleta atividade
     */
    @DeleteMapping("/atividades/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            atividadeService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/atividades/{id}/mover-cima
     * Move atividade uma posição para cima
     */
    @PostMapping("/atividades/{id}/mover-cima")
    public ResponseEntity<Void> moverParaCima(@PathVariable Long id) {
        try {
            atividadeService.moverParaCima(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/atividades/{id}/mover-baixo
     * Move atividade uma posição para baixo
     */
    @PostMapping("/atividades/{id}/mover-baixo")
    public ResponseEntity<Void> moverParaBaixo(@PathVariable Long id) {
        try {
            atividadeService.moverParaBaixo(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}