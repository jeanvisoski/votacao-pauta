package com.api.contractVoting.controller;


import com.api.contractVoting.dtos.ScheduleDTO;
import com.api.contractVoting.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pauta")
public class ScheduleController {

    private final ScheduleService service;

    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> SaveContract(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        log.debug("Salvando pauta  = {}", scheduleDTO.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(ScheduleDTO.toDTO(service.save(scheduleDTO)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ScheduleDTO> searchContractById(@PathVariable("id") Integer id) {
        log.debug("Pesquisando pauta pelo ID = {}", id);
        return ResponseEntity.ok(ScheduleDTO.toDTO(service.searchContractById(id)));
    }
}