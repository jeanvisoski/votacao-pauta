package com.api.contractVoting.controller;


import com.api.contractVoting.dtos.ContractDTO;
import com.api.contractVoting.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pautas")
public class ContractController {

    private final ContractService service;

    @Autowired
    public ContractController(ContractService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContractDTO> SaveContract(@Valid @RequestBody ContractDTO contractDTO) {
        log.debug("Salvando pauta  = {}", contractDTO.getDescricao());
        contractDTO = service.save(contractDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(contractDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContractDTO> searchContractById(@PathVariable("id") Integer id) {
        log.debug("Pesquisando pauta pelo ID = {}", id);
        return ResponseEntity.ok(service.searchContractById(id));
    }
}