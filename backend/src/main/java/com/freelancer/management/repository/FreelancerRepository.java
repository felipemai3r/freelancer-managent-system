package com.freelancer.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelancer.management.model.Freelancer;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {

}
