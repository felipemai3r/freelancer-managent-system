package com.freelancer.management.controller;

import com.freelancer.management.dto.TarefaRequestDTO;
import com.freelancer.management.model.Tarefa;
import com.freelancer.management.model.enums.StatusTarefa;
import com.freelancer.management.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para gerenciar Tarefas
 * 
 * Endpoints:
 * GET    /api/atividades/{atividadeId}/tarefas  - Lista tarefas da atividade
 * POST   /api/atividades/{atividadeId}/tarefas  - Cria nova tarefa
 * GET    /api/tarefas/{id}                      - Busca tarefa por ID
 * PUT    /api/tarefas/{id}                      - Atualiza tarefa
 * DELETE /api/tarefas/{id}                      - Deleta tarefa
 * PATCH  /api/tarefas/{id}/status               - Atualiza apenas o status
 * GET    /api/freelancers/{freelancerId}/tarefas - Lista tarefas do freelancer
 * 
 * @author Felipe Maier
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:80"})
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    /**
     * GET /api/atividades/{atividadeId}/tarefas
     * Lista tarefas de uma atividade
     */
    @GetMapping("/atividades/{atividadeId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorAtividade(@PathVariable Long atividadeId) {
        List<Tarefa> tarefas = tarefaService.listarPorAtividade(atividadeId);
        return ResponseEntity.ok(tarefas);
    }

    /**
     * POST /api/atividades/{atividadeId}/tarefas
     * Cria nova tarefa na atividade
     */
    @PostMapping("/atividades/{atividadeId}/tarefas")
    public ResponseEntity<Tarefa> criar(
            @PathVariable Long atividadeId,
            @Valid @RequestBody TarefaRequestDTO dto) {
        try {
            // Garante que o atividadeId do path é usado
            dto.setAtividadeId(atividadeId);
            Tarefa criada = tarefaService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/tarefas/{id}
     * Busca tarefa por ID
     */
    @GetMapping("/tarefas/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.buscarPorId(id);
            return ResponseEntity.ok(tarefa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT /api/tarefas/{id}
     * Atualiza tarefa existente
     */
    @PutMapping("/tarefas/{id}")
    public ResponseEntity<Tarefa> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TarefaRequestDTO dto) {
        try {
            Tarefa atualizada = tarefaService.atualizar(id, dto);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PATCH /api/tarefas/{id}/status
     * Atualiza apenas o status da tarefa
     * Body: { "status": "EM_PROGRESSO" }
     */
    @PatchMapping("/tarefas/{id}/status")
    public ResponseEntity<Tarefa> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            String statusStr = body.get("status");
            if (statusStr == null) {
                return ResponseEntity.badRequest().build();
            }
            
            StatusTarefa status = StatusTarefa.valueOf(statusStr);
            Tarefa atualizada = tarefaService.atualizarStatus(id, status);
            return ResponseEntity.ok(atualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/tarefas/{id}
     * Deleta tarefa
     */
    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            tarefaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/freelancers/{freelancerId}/tarefas
     * Lista tarefas de um freelancer
     */
    @GetMapping("/freelancers/{freelancerId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorFreelancer(@PathVariable Long freelancerId) {
        List<Tarefa> tarefas = tarefaService.listarPorFreelancer(freelancerId);
        return ResponseEntity.ok(tarefas);
    }
}