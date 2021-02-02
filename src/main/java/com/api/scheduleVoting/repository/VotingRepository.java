package com.api.scheduleVoting.repository;

import com.api.scheduleVoting.dtos.VotingDTO;
import com.api.scheduleVoting.entity.VotingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingRepository extends JpaRepository<VotingEntity, Integer> {

    Integer countVotingByVotingSessionIdAndVote(Integer votingSessionId, Boolean vote);

    List<VotingEntity> findByVotingSessionId(Integer votingSessionId);
}
