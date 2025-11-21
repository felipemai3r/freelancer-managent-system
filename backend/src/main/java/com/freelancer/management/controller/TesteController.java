package com.freelancer.management.controller;

import com.freelancer.management.model.Pessoa;
import com.freelancer.management.model.Empresa;
import com.freelancer.management.model.Freelancer;
import com.freelancer.management.repository.PessoaRepository;
import com.freelancer.management.repository.EmpresaRepository;
import com.freelancer.management.repository.FreelancerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TesteController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @GetMapping("/pessoas")
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/empresas")
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    @GetMapping("/freelancers")
    public List<Freelancer> listarFreelancers() {
        return freelancerRepository.findAll();
    }

    @PostMapping("/criarPessoa")
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @PostMapping("/empresa")
    public Empresa criarEmpresa(@RequestBody Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @PostMapping("/freelancer")
    public Freelancer criarFreelancer(@RequestBody Freelancer freelancer) {
        return freelancerRepository.save(freelancer);
    }
}
