package com.freelancer.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.management.model.Pessoa;
import com.freelancer.management.repository.PessoaRepository;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

    /**
     * Injeção do repositório de Pessoa
     */
    @Autowired
    private PessoaRepository pessoaRepository;

    /**
     * Lista todas as pessoas via GET
     * 
     * @return Lista de pessoas
     */
    @GetMapping("/listar")
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    /**
     * Cria uma nova pessoa via POST
     * 
     * @param pessoa Objeto Pessoa a ser criado
     * @return Pessoa criada
     */
    @PostMapping("/criarPessoa")
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    /**
     * Desativa uma pessoa via PUT
     * 
     * @param id ID da pessoa a ser desativada
     * @return Resposta HTTP com mensagem de sucesso ou erro
     */
    @PutMapping("/{id}/desativar")
    public ResponseEntity<String> desativarPessoa(@PathVariable Long id) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.desativar();
                    pessoaRepository.save(pessoa);
                    return ResponseEntity.ok("Pessoa desativada com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Ativa uma pessoa via PUT
     * 
     * @param id ID da pessoa a ser ativada
     * @return Resposta HTTP com mensagem de sucesso ou erro
     */
    @PutMapping("/{id}/ativar")
    public ResponseEntity<String> ativarPessoa(@PathVariable Long id) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.ativar();
                    pessoaRepository.save(pessoa);
                    return ResponseEntity.ok("Pessoa reativada com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
