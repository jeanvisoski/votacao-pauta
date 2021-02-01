package com.api.scheduleVoting.repository;

import com.api.scheduleVoting.entity.VotingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<VotingEntity, Integer> {

    Integer countVotingByScheduleIdAndVotingSessionIdAndVote(Integer scheduleId, Integer votingSessionId, Boolean vote);
}
