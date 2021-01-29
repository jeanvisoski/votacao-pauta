package com.api.contractVoting.service;

import com.api.contractVoting.dtos.ResultDTO;
import com.api.contractVoting.dtos.ScheduleDTO;
import com.api.contractVoting.dtos.VotingDTO;
import com.api.contractVoting.dtos.VotingSessionOpenDTO;
import com.api.contractVoting.dtos.VotingSessionDTO;
import com.api.contractVoting.entity.VotingSessionEntity;
import com.api.contractVoting.exception.NotFoundException;
import com.api.contractVoting.repository.VotingRepository;
import com.api.contractVoting.repository.VotingSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VotingSessionService {

    private static final Integer TEMPO_DEFAULT = 1;

    private final VotingSessionRepository repository;
    private final VotingRepository votingRepository;
    private final ScheduleService contractService;
    private static final String UTC = "UTC-3";

    @Autowired
    public VotingSessionService(VotingSessionRepository repository, VotingRepository votingRepository, ScheduleService contractService) {
        this.repository = repository;
        this.votingRepository = votingRepository;
        this.contractService = contractService;
    }

    @Transactional
    public VotingSessionEntity openVotingSession(VotingSessionOpenDTO VotingSessionOpenDTO) {
        log.debug("Abrindo a sessao de votacao para a pauta {}", VotingSessionOpenDTO.getContractId());

        if (contractService.isContractValid(VotingSessionOpenDTO.getContractId())) {
            return save(VotingSessionDTO.builder()
                    .id(null)
                    .dateTimeStart(LocalDateTime.now(ZoneId.of(UTC)))
                    .dateTimeEnd(calculateTime(VotingSessionOpenDTO.getTime()))
                    .active(Boolean.TRUE)
                    .build());
        } else {
            throw new NotFoundException("Pauta não localizada contractId" + VotingSessionOpenDTO.getContractId());
        }

    }

    @Transactional(readOnly = true)
    public List<VotingSessionDTO> searchSessionsInProgress() {
        log.debug("Buscando sessoes em andamento");
        return repository.findByActive(Boolean.TRUE)
                .stream()
                .map(VotingSessionDTO::toDTO)
                .collect(Collectors.toList())
                .stream()
                .filter(dto -> dto.getDateTimeEnd().isBefore(LocalDateTime.now(ZoneId.of(UTC))))
                .collect(Collectors.toList());
    }

    @Transactional
    public void closeVotingSession(VotingSessionDTO dto) {
        log.debug("Encerrando sessao com tempo de duracao expirado {}", dto.getId());
        save(dto.toBuilder()
                .active(Boolean.FALSE)
                .build());
    }

    @Transactional(readOnly = true)
    public boolean isValidVoteSession(Integer id) {
        return repository.existsByIdAndActive(id, Boolean.TRUE);
    }

    @Transactional(readOnly = true)
    public boolean isSessionVoting(Integer id) {
        if (repository.existsById(id)) {
            return Boolean.TRUE;
        } else {
            log.error("Sessao de votacao nao localizada para o id {}", id);
            throw new NotFoundException("Sessão de votação não localizada para o id " + id);
        }
    }

    @Transactional(readOnly = true)
    public boolean isSessaoValidaForCount(Integer id) {
        return repository.existsByIdAndActive(id, Boolean.FALSE);
    }

    private LocalDateTime calculateTime(Integer tempo) {
        if (tempo != null && tempo != 0) {
            return LocalDateTime.now(ZoneId.of(UTC)).plusMinutes(tempo);
        } else {
            return LocalDateTime.now(ZoneId.of(UTC)).plusMinutes(TEMPO_DEFAULT);
        }
    }

    @Transactional
    public VotingSessionEntity save(VotingSessionDTO dto) { log.debug("Salvando a sessao de votacao");
        if (Optional.ofNullable(dto).isPresent()) {
            return repository.save(VotingSessionDTO.toEntity(dto));
        }
        return null;
    }

    @Transactional(readOnly = true)
    public ResultDTO searchDataResultVoting(Integer contractId, Integer votingSessionId) {
        if (isValidDateExist(contractId, votingSessionId) && isSessaoValidaForCount(votingSessionId)) {
            log.debug("Construindo o objeto de retorno do resultado para contractId = {}, votingSessionId = {}", contractId, votingSessionId);
            return new ResultDTO(ScheduleDTO.toDTO(contractService.searchContractById(contractId)), searchResultVoting(contractId, votingSessionId));
        }
        throw new NotFoundException("Sessão de votação ainda está aberta, não é possível obter a contagem do resultado.");
    }

    @Transactional(readOnly = true)
    public boolean isValidDateExist(Integer contractId, Integer votingSessionId) {
        return isSessionVoting(votingSessionId) && contractService.isContractValid(contractId);
    }

    @Transactional(readOnly = true)
    public VotingDTO searchResultVoting(Integer contractId, Integer votingSessionId) {
        log.debug("Contabilizando os votos para contractId = {}, votingSessionId = {}", contractId, votingSessionId);

        return VotingDTO.builder()
                .contractId(contractId)
                .votingSessionId(votingSessionId)
                .quantityVoteYes(votingRepository.countVotingByContractIdAndVotingSessionIdAndVote(contractId, votingSessionId, Boolean.TRUE))
                .quantityVoteNo(votingRepository.countVotingByContractIdAndVotingSessionIdAndVote(contractId, votingSessionId, Boolean.FALSE))
                .build();
    }
}
