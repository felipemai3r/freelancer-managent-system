package com.freelancer.management.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.freelancer.management.model.Freelancer;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {

    /**
     * Busca freelancer por CPF/CNPJ
     */
    Optional<Freelancer> findByCpfCnpj(String cpfCnpj);
    
    /**
     * Verifica se CPF/CNPJ já existe
     */
    boolean existsByCpfCnpj(String cpfCnpj);
}
