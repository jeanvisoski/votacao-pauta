package com.api.contractVoting.service;

import com.api.contractVoting.dtos.ContractDTO;
import com.api.contractVoting.exception.NotFoundException;
import com.api.contractVoting.repository.ContractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ContractService {

    private final ContractRepository repository;

    @Autowired
    public ContractService(ContractRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ContractDTO save(ContractDTO dto) {
        return ContractDTO.toDTO(repository.save(ContractDTO.toEntity(dto)));
    }

    @Transactional(readOnly = true)
    public ContractDTO searchContractById(Integer id) {
        if (!repository.findById(id).isPresent()) {
            log.error("Pauta não localizada para id {}", id);
            throw new NotFoundException("Pauta não localizada para o id " + id);
        }

        return ContractDTO.toDTO(repository.findById(id).get());
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