package com.api.contractVoting.service;

import com.api.contractVoting.dtos.VotingSessionOpenDTO;
import com.api.contractVoting.dtos.VotingSessionDTO;
import com.api.contractVoting.exception.NotFoundException;
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
    private final ContractService contractService;
    private static final String UTC = "UTC-3";

    @Autowired
    public VotingSessionService(VotingSessionRepository repository, ContractService contractService) {
        this.repository = repository;
        this.contractService = contractService;
    }

    @Transactional
    public VotingSessionDTO openVotingSession(VotingSessionOpenDTO VotingSessionOpenDTO) {
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
    public VotingSessionDTO save(VotingSessionDTO dto) { log.debug("Salvando a sessao de votacao");
        if (Optional.ofNullable(dto).isPresent()) {
            return VotingSessionDTO.toDTO(repository.save(VotingSessionDTO.toEntity(dto)));
        }
        return null;
    }
}
