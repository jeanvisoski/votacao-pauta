package com.api.contractVoting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotingSessionOpenDTO {

    @NotNull(message = "contractId deve ser preenchido")
    private Integer contractId;

    private Integer time;
}
