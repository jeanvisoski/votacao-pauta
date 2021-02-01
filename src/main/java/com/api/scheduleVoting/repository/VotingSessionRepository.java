package com.api.scheduleVoting.repository;

import com.api.scheduleVoting.entity.VotingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSessionEntity, Integer> {

    List<VotingSessionEntity> findByActive (Boolean active);

    Boolean existsByIdAndActive(Integer id, Boolean active);

    boolean existsById(Integer id);
}
