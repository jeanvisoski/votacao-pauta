package com.api.scheduleVoting.service;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.dtos.VoteDTO;
import com.api.scheduleVoting.dtos.VotingSessionOpenDTO;
import com.api.scheduleVoting.entity.VotingSessionEntity;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VotingServiceTest extends BaseTest {

    @MockBean
    private VotingService service;

    @Test
    public void testOpenVotingSession() throws Exception {

        when(service.vote(any())).thenReturn("Voto registrado com sucesso!");

        String response = service.vote(VoteDTO.builder()
                .associatedCpf("03577834099")
                .scheduleId(1)
                .voting(Boolean.TRUE)
                .votingSessionId(1)
                .build());

        assertThat(response).isEqualTo("Voto registrado com sucesso!");
    }
}