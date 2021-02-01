package com.api.scheduleVoting.repository;

import com.api.scheduleVoting.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

    boolean existsById(Integer id);
}
