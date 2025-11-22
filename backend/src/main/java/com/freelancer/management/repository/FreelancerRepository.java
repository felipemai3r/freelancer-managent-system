package com.freelancer.management.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

     /**
     * Busca freelancers ativos
     * IMPORTANTE: ativo está em Pessoa, não em Freelancer!
     */
     @Query("SELECT f FROM Freelancer f WHERE f.pessoa.ativo = true")
     List<Freelancer> findFreelancersAtivos();
 
     /**
      * Busca freelancers ativos por tipo (PJ/PF)
      * CORRIGIDO: f.pessoa.ativo (não f.ativo)
      */
     @Query("SELECT f FROM Freelancer f WHERE f.pessoa.ativo = true AND f.isPj = :isPj")
     List<Freelancer> findFreelancersAtivosByTipo(@Param("isPj") Boolean isPj);

}
