package com.freelancer.management.controller;

import java.util.List;
import com.freelancer.management.model.Empresa;
import com.freelancer.management.repository.EmpresaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping("/listar")
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

}
