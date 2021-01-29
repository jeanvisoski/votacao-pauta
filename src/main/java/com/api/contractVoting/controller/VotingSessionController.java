package com.api.contractVoting.controller;

import com.api.contractVoting.dtos.ResultDTO;
import com.api.contractVoting.dtos.VotingSessionOpenDTO;
import com.api.contractVoting.dtos.VotingSessionDTO;
import com.api.contractVoting.service.VotingService;
import com.api.contractVoting.service.VotingSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/sessao")
public class VotingSessionController {

    private final VotingSessionService service;

    @Autowired
    public VotingSessionController(VotingSessionService service, VotingService votingService) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<VotingSessionDTO> openVotingSession(@Valid @RequestBody VotingSessionOpenDTO VotingSessionOpenDTO) {
        log.debug("Sessao aberta para votacao da pauta id = {}", VotingSessionOpenDTO.getContractId());

        VotingSessionDTO dto = VotingSessionDTO.toDTO(service.openVotingSession(VotingSessionOpenDTO));

        log.debug("Hora de inicio da sessao para votacao {}", dto.getDateTimeStart());
        log.debug("Hora de encerramento da sessao para votacao {}", dto.getDateTimeEnd());

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping(value = "/{votingSessionId}/{contractId}/resultado")
    public ResponseEntity<ResultDTO> resultVoting(@PathVariable("contractId") Integer contractId, @PathVariable("votingSessionId") Integer votingSessionId) {
        log.debug("Buscando resultado da votacao contractId = {} , votingSessionId = {} ", contractId, votingSessionId);
        return ResponseEntity.status(HttpStatus.OK).body(service.searchDataResultVoting(contractId, votingSessionId));
    }


}
