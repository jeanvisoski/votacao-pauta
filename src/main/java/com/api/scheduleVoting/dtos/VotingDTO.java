package com.api.scheduleVoting.dtos;

import com.api.scheduleVoting.entity.VotingEntity;
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

    private Integer scheduleId;

    private Integer votingSessionId;

    @JsonIgnore
    private Boolean vote;

    private Integer quantityVoteYes;

    private Integer quantityVoteNo;

    public static VotingEntity toEntity(VotingDTO dto) {
        return VotingEntity.builder()
                .id(dto.getId())
                .scheduleId(dto.getScheduleId())
                .votingSessionId(dto.getVotingSessionId())
                .vote(dto.getVote())
                .build();
    }
}
