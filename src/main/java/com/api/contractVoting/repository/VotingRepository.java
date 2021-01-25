package com.api.contractVoting.repository;

import com.api.contractVoting.entity.VotingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<VotingEntity, Integer> {

    Integer countVotingByContractIdAndVotingSessionIdAndVote(Integer contractId, Integer votingSessionId, Boolean vote);
}
