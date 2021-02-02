package com.api.scheduleVoting.controller;

import com.api.scheduleVoting.dtos.ResultDTO;
import com.api.scheduleVoting.dtos.VotingDTO;
import com.api.scheduleVoting.dtos.VotingSessionOpenDTO;
import com.api.scheduleVoting.dtos.VotingSessionDTO;
import com.api.scheduleVoting.service.VotingService;
import com.api.scheduleVoting.service.VotingSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "sessao", tags = "sessao")
public class VotingSessionController {

    private final VotingSessionService service;

    @Autowired
    public VotingSessionController(VotingSessionService service, VotingService votingService) {
        this.service = service;
    }

    @ApiOperation(value = "Criar uma sessao de uma determinada pauta")
    @PostMapping()
    public ResponseEntity<VotingSessionDTO> openVotingSession(@Valid @RequestBody VotingSessionOpenDTO VotingSessionOpenDTO) {
        log.debug("Sessao aberta para votacao da pauta id = {}", VotingSessionOpenDTO.getScheduleId());

        VotingSessionDTO dto = VotingSessionDTO.toDTO(service.openVotingSession(VotingSessionOpenDTO));

        log.debug("Hora de inicio da sessao para votacao {}", dto.getDateTimeStart());
        log.debug("Hora de encerramento da sessao para votacao {}", dto.getDateTimeEnd());

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @ApiOperation(value = "Buscar os resultados da votação de uma Pauta")
    @GetMapping(value = "/{votingSessionId}/resultado")
    public ResponseEntity<VotingDTO> resultVoting(@PathVariable("votingSessionId") Integer votingSessionId) {
        log.debug("Buscando resultado da votacao votingSessionId = {} ", votingSessionId);
        return ResponseEntity.status(HttpStatus.OK).body(service.searchDataResultVoting(votingSessionId));
    }


}
