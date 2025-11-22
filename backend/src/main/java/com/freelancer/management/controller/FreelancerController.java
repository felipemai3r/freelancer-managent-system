package com.freelancer.management.controller;

import java.util.List;

import com.freelancer.management.repository.FreelancerRepository;
import com.freelancer.management.model.Freelancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/freelancer")
public class FreelancerController {

     @Autowired
    private FreelancerRepository freelancerRepository;

    @GetMapping("/freelancers")
    public List<Freelancer> listarFreelancers() {
        return freelancerRepository.findAll();
    }
  @PostMapping("/freelancer")
    public Freelancer criarFreelancer(@RequestBody Freelancer freelancer) {
        return freelancerRepository.save(freelancer);
    }
}
