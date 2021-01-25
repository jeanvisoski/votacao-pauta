package com.api.contractVoting.repository;

import com.api.contractVoting.entity.AssociatedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociatedRepository extends JpaRepository<AssociatedEntity, Integer> {

    Boolean existsByAssociatedCpfAndContractId(String associatedCpf, Integer contractId);
}
