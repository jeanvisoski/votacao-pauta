package com.api.scheduleVoting.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotingSessionOpenDTO {

    @NotNull(message = "scheduleID deve ser preenchido")
    private Integer scheduleId;

    private Integer time;
}
