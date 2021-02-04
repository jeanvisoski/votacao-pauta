package com.api.scheduleVoting.dtos;

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
}
