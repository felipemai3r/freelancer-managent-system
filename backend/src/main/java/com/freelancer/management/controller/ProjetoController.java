package com.freelancer.management.controller;

import com.freelancer.management.dto.ProjetoRequestDTO;
import com.freelancer.management.dto.ProjetoResponseDTO;
import com.freelancer.management.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar Projetos
 * 
 * Endpoints:
 * GET    /api/projetos          - Lista todos os projetos
 * GET    /api/projetos/{id}     - Busca projeto por ID
 * POST   /api/projetos          - Cria novo projeto
 * PUT    /api/projetos/{id}     - Atualiza projeto
 * DELETE /api/projetos/{id}     - Deleta projeto
 * GET    /api/empresas/{id}/projetos - Lista projetos de uma empresa
 * 
 * @author Felipe Maier
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:80"})
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    /**
     * GET /api/projetos
     * Lista todos os projetos
     */
     @GetMapping("/projetos")
     public ResponseEntity<List<ProjetoResponseDTO>> listarTodos() {
         List<ProjetoResponseDTO> projetos = projetoService.listarTodos();
         return ResponseEntity.ok(projetos);
     }

    /**
     * GET /api/projetos/{id}
     * Busca projeto por ID com atividades e freelancers
     */
    @GetMapping("/projetos/{id}")
    public ResponseEntity<ProjetoResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            ProjetoResponseDTO projeto = projetoService.buscarPorIdComAtividades(id);
            return ResponseEntity.ok(projeto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/projetos
     * Cria novo projeto
     */
    @PostMapping("/projetos")
    public ResponseEntity<ProjetoResponseDTO> criar(@Valid @RequestBody ProjetoRequestDTO dto) {
        try {
            ProjetoResponseDTO criado = projetoService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/projetos/{id}
     * Atualiza projeto existente
     */
    @PutMapping("/projetos/{id}")
    public ResponseEntity<ProjetoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProjetoRequestDTO dto) {
        try {
            ProjetoResponseDTO atualizado = projetoService.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/projetos/{id}
     * Deleta projeto
     */
    @DeleteMapping("/projetos/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            projetoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/empresas/{empresaId}/projetos
     * Lista projetos de uma empresa específica
     */
    @GetMapping("/empresas/{empresaId}/projetos")
    public ResponseEntity<List<ProjetoResponseDTO>> listarPorEmpresa(@PathVariable Long empresaId) {
        List<ProjetoResponseDTO> projetos = projetoService.listarPorEmpresa(empresaId);
        return ResponseEntity.ok(projetos);
    }
}