package com.api.contractVoting.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    private ContractDTO contractDTO;
    private VotingDTO votingDTO;
}
