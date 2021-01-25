package com.api.contractVoting.controller;

import com.api.contractVoting.dtos.ResultDTO;
import com.api.contractVoting.dtos.VoteDTO;
import com.api.contractVoting.service.VotingService;
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
@RequestMapping(value = "/api/v1/votacoes")
public class VotingController {

    private final VotingService service;

    @Autowired
    public VotingController(VotingService service) {
        this.service = service;
    }

    @PostMapping(value = "/votar")
    public ResponseEntity<String> vote(@Valid @RequestBody VoteDTO dto) {
        log.debug("Associado votando associado = {}", dto.getAssociatedCpf());
        String mensagem = service.vote(dto);
        log.debug("Voto associado finalizado associado = {}", dto.getAssociatedCpf());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
    }

    @GetMapping(value = "/resultado/{contractId}/{votingSessionId}")
    public ResponseEntity<ResultDTO> resultVoting(@PathVariable("contractId") Integer contractId, @PathVariable("votingSessionId") Integer votingSessionId) {
        log.debug("Buscando resultado da votacao contractId = {} , votingSessionId = {} ", contractId, votingSessionId);
        ResultDTO dto = service.searchDataResultVoting(contractId, votingSessionId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
