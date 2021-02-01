package com.api.scheduleVoting.service;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.client.ValidCPFClient;
import com.api.scheduleVoting.dtos.ResultDTO;
import com.api.scheduleVoting.dtos.ScheduleDTO;
import com.api.scheduleVoting.dtos.VotingDTO;
import com.api.scheduleVoting.dtos.VotingSessionOpenDTO;
import com.api.scheduleVoting.entity.VotingSessionEntity;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VotingSessionServiceTest extends BaseTest {

    @MockBean
    private VotingSessionService service;

    @MockBean
    private ValidCPFClient validCPFClient;

    private static final Integer ID = 1;
    private static final String DESCRICAO = "PRIMEIRA PAUTA TESTE";

    @Test
    public void testOpenVotingSession() throws Exception {

        when(service.openVotingSession(any())).thenReturn(VotingSessionEntity.builder()
                .id(ID)
                .dateTimeStart(LocalDateTime.of(2021,
                        Month.JULY, 29, 19, 30, 40))
                .dateTimeEnd(LocalDateTime.of(2021,
                        Month.JULY, 29, 20, 30, 40))
                .active(Boolean.TRUE)
                .build());


        VotingSessionEntity response = service.openVotingSession(VotingSessionOpenDTO.builder()
                .scheduleId(ID)
                .time(1)
                .build());

        assertThat(response).isEqualTo(VotingSessionEntity.builder()
                .id(ID)
                .dateTimeStart(LocalDateTime.of(2021,
                        Month.JULY, 29, 19, 30, 40))
                .dateTimeEnd(LocalDateTime.of(2021,
                        Month.JULY, 29, 20, 30, 40))
                .active(Boolean.TRUE)
                .build());
    }


    @Test
    public void testSearchDataResultVoting() throws Exception {

        when(service.searchDataResultVoting(any(), any())).thenReturn(ResultDTO.builder()
                .scheduleDTO(ScheduleDTO.builder()
                        .id(ID)
                        .descricao(DESCRICAO)
                        .build())
                .votingDTO(VotingDTO.builder()
                        .id(ID)
                        .quantityVoteYes(1)
                        .build())
                .build());


        ResultDTO response = service.searchDataResultVoting(1,1);

        assertThat(response).isEqualTo(ResultDTO.builder()
                .scheduleDTO(ScheduleDTO.builder()
                        .id(ID)
                        .descricao(DESCRICAO)
                        .build())
                .votingDTO(VotingDTO.builder()
                        .id(ID)
                        .quantityVoteYes(1)
                        .build())
                .build());
    }

}