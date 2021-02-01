package com.api.scheduleVoting.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDTO {

    @NotNull(message = "scheduleId deve ser preenchido")
    private Integer scheduleId;

    @NotNull(message = "votingSessionId deve ser preenchido")
    private Integer votingSessionId;

    @NotNull(message = "voting deve ser preenchido")
    private Boolean voting;

    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "cpf do associado deve ser preenchido")
    private String associatedCpf;
}
