package com.api.scheduleVoting.repository;

import com.api.scheduleVoting.entity.VotingEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingRepository extends JpaRepository<VotingEntity, Integer> {

    Integer countVotingByVotingSessionIdAndVote(Integer votingSessionId, Boolean vote);

    @Query(value = "SELECT * FROM voting where voting_session_id = :votingSessionId",
            nativeQuery = true)
    List<VotingEntity> findByVotingSessionId(@Param("votingSessionId") Integer votingSessionId);
}
