package com.api.scheduleVoting.service;

import com.api.scheduleVoting.client.ValidCPFClient;
import com.api.scheduleVoting.dtos.AssociatedDTO;
import com.api.scheduleVoting.entity.AssociatedEntity;
import com.api.scheduleVoting.repository.AssociatedRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AssociatedService {

    private final AssociatedRepository repository;
    private final ValidCPFClient validCPFClient;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    public AssociatedService(AssociatedRepository repository, ValidCPFClient validCPFClient, ModelMapper modelMapper) {
        this.repository = repository;
        this.validCPFClient = validCPFClient;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public boolean isValidVotingMemberParticipation(String associatedCpf, Integer scheduleId) {
        log.debug("Validando participacao do associado na votacao da pauta  scheduleId = {}", scheduleId);
        if (repository.existsByAssociatedCpfAndScheduleId(associatedCpf, scheduleId)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Transactional
    public void saveAssociated(AssociatedDTO dto) {
        log.debug("Registrando participacao do associado na votacao associatedId = {}, scheduleId = {}", dto.getAssociatedCpf(), dto.getScheduleId());
        repository.save(modelMapper.map(dto, AssociatedEntity.class));
    }

    public boolean isAssociatedCanVote(String cpf) {
        return validCPFClient.isVerifiesAssociatedEnabledVoting(cpf);
    }
}