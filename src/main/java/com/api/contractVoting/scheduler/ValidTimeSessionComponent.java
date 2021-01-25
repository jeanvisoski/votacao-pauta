package com.api.contractVoting.scheduler;

import com.api.contractVoting.dtos.VotingSessionDTO;
import com.api.contractVoting.service.VotingSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Component
public class ValidTimeSessionComponent {

    private final VotingSessionService votingSessionService;

    private static final String UTC = "UTC-3";

    public ValidTimeSessionComponent(VotingSessionService votingSessionService) {
        this.votingSessionService = votingSessionService;
    }

    @Scheduled(cron = "5 * * * * *")
    private void validTimeSession() {
        log.debug("Contador de tempo sendo excutado...");
        votingSessionService.searchSessionsInProgress().forEach(dto -> {
            if (dto.getDateTimeEnd().isBefore(LocalDateTime.now(ZoneId.of(UTC)))) {
                log.debug("Sessao encerrada {}", dto.getId());
                votingSessionService.closeVotingSession(dto);
            }
        });
    }
}