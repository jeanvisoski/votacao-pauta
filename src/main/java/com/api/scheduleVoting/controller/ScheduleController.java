package com.api.scheduleVoting.controller;


import com.api.scheduleVoting.dtos.ScheduleDTO;
import com.api.scheduleVoting.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pauta")
@Api(value = "Pauta", tags = "Pauta")
public class ScheduleController {

    private final ScheduleService service;

    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @ApiOperation(value = "Criar uma pauta para ser votada")
    @PostMapping
    public ResponseEntity<ScheduleDTO> SaveSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        log.debug("Salvando pauta  = {}", scheduleDTO.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(ScheduleDTO.toDTO(service.save(scheduleDTO)));
    }

    @ApiOperation(value = "Buscar uma pauta utilizando o ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ScheduleDTO> searchScheduleById(@PathVariable("id") Integer id) {
        log.debug("Pesquisando pauta pelo ID = {}", id);
        return ResponseEntity.ok(ScheduleDTO.toDTO(service.searchScheduleById(id)));
    }
}