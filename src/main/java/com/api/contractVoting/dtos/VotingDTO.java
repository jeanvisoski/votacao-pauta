package com.api.contractVoting.dtos;

import com.api.contractVoting.entity.VotingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder( toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class VotingDTO {

    @JsonIgnore
    private Integer id;

    private Integer contractId;

    private Integer votingSessionId;

    @JsonIgnore
    private Boolean vote;

    private Integer quantityVoteYes;

    private Integer quantityVoteNo;

    public static VotingEntity toEntity(VotingDTO dto) {
        return VotingEntity.builder()
                .id(dto.getId())
                .contractId(dto.getContractId())
                .votingSessionId(dto.getVotingSessionId())
                .vote(dto.getVote())
                .build();
    }
}
