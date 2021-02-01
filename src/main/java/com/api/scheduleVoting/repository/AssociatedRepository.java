package com.api.scheduleVoting.repository;

import com.api.scheduleVoting.entity.AssociatedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociatedRepository extends JpaRepository<AssociatedEntity, Integer> {

    Boolean existsByAssociatedCpfAndScheduleId(String associatedCpf, Integer scheduleId);
}
