package com.api.contractVoting.repository;

import com.api.contractVoting.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

    boolean existsById(Integer id);
}
