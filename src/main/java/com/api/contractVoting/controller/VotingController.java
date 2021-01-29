package com.api.contractVoting.controller;

import com.api.contractVoting.dtos.VoteDTO;
import com.api.contractVoting.service.VotingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/votacao")
public class VotingController {

    private final VotingService service;

    @Autowired
    public VotingController(VotingService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<String> vote(@Valid @RequestBody VoteDTO dto) {
        log.debug("Associado votando associado = {}", dto.getAssociatedCpf());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.vote(dto));
    }
}
