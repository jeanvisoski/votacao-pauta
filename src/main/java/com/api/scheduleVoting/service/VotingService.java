package com.api.scheduleVoting.service;

import com.api.scheduleVoting.dtos.AssociatedDTO;
import com.api.scheduleVoting.dtos.VoteDTO;
import com.api.scheduleVoting.dtos.VotingDTO;
import com.api.scheduleVoting.entity.VotingEntity;
import com.api.scheduleVoting.exception.NotFoundException;
import com.api.scheduleVoting.exception.SessionFindException;
import com.api.scheduleVoting.exception.InvalidVoteException;
import com.api.scheduleVoting.repository.VotingRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class VotingService {

    private final VotingRepository repository;
    private final ScheduleService scheduleService;
    private final VotingSessionService votingSessionService;
    private final AssociatedService associatedService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public VotingService(VotingRepository repository, ScheduleService scheduleService, VotingSessionService sessaoVotacaoService, AssociatedService associadoService) {
        this.repository = repository;
        this.scheduleService = scheduleService;
        this.votingSessionService = sessaoVotacaoService;
        this.associatedService = associadoService;
    }

    @Transactional(readOnly = true)
    public boolean isValidVote(VoteDTO dto) {
        log.debug("Validando os dados para voto sessionId = {}, scheduleId = {}, associatedId = {}", dto.getVotingSessionId(), dto.getScheduleId(), dto.getAssociatedCpf());

        if (!scheduleService.isScheduleId(dto.getScheduleId())) {
            log.error("Pauta nao localizada para votacao scheduleId {}", dto.getAssociatedCpf());
            throw new NotFoundException("Pauta não localizada id " + dto.getScheduleId());
        } else if (!votingSessionService.isValidVoteSession(dto.getVotingSessionId())) {
            log.error("Tentativa de voto para sessao encerrada votingSessionId {}", dto.getVotingSessionId());
            throw new SessionFindException("Sessão de votação já encerrada");
        } else if (!associatedService.isValidVotingMemberParticipation(dto.getAssociatedCpf(), dto.getScheduleId())) {
            log.error("Associado tentou votar mais de 1 vez associatedId {}", dto.getAssociatedCpf());
            throw new InvalidVoteException("Não é possível votar mais de 1 vez na mesma pauta");
        } else if (!associatedService.isAssociatedCanVote(dto.getAssociatedCpf())) {
            log.error("Associado nao esta habilitado para votar {}", dto.getAssociatedCpf());
            throw new InvalidVoteException("CPF não aceito para votação");
        }

        return Boolean.TRUE;
    }

    @Transactional
    public String vote(VoteDTO dto) {
        if (isValidVote(dto)) {
            log.debug("Dados validos para voto sessionId = {}, scheduleId = {}, associatedId = {}", dto.getVotingSessionId(), dto.getScheduleId(), dto.getAssociatedCpf());

            registerVote(VotingDTO.builder()
                    .scheduleId(dto.getScheduleId())
                    .votingSessionId(dto.getVotingSessionId())
                    .vote(dto.getVoting())
                    .build());

            registerAssociatedVoted(dto);

            log.debug("Voto associado finalizado associado = {}", dto.getAssociatedCpf());

            return "Voto registrado com sucesso!";
        }
        return null;
    }

    @Transactional
    public void registerAssociatedVoted(VoteDTO dto) {
        associatedService.saveAssociated(AssociatedDTO.builder()
                .id(null)
                .associatedCpf(dto.getAssociatedCpf())
                .scheduleId(dto.getScheduleId())
                .build());
    }

    @Transactional
    public void registerVote(VotingDTO dto) {
        log.debug("Salvando o voto para scheduleId {}", dto.getScheduleId());
        repository.save(modelMapper.map(dto, VotingEntity.class));
    }

}