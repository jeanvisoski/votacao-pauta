package com.api.contractVoting.repository;

import com.api.contractVoting.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Integer> {

    boolean existsById(Integer id);
}
