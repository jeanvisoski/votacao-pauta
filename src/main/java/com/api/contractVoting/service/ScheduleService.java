package com.api.contractVoting.service;

import com.api.contractVoting.dtos.ScheduleDTO;
import com.api.contractVoting.entity.ScheduleEntity;
import com.api.contractVoting.exception.NotFoundException;
import com.api.contractVoting.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    @Autowired
    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ScheduleEntity save(ScheduleDTO dto) {
        return repository.save(ScheduleDTO.toEntity(dto));
    }

    @Transactional(readOnly = true)
    public ScheduleEntity searchContractById(Integer id) {
        if (!repository.findById(id).isPresent()) {
            log.error("Pauta não localizada para id {}", id);
            throw new NotFoundException("Pauta não localizada para o id " + id);
        }

        return repository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public boolean isContractValid(Integer id) {
        if (repository.existsById(id)) {
            return Boolean.TRUE;
        } else {
            log.error("Pauta não localizada para id {}", id);
            throw new NotFoundException("Pauta não localizada para o id " + id);
        }
    }
}