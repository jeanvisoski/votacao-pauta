package com.api.scheduleVoting.service;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.client.ValidCPFClient;
import com.api.scheduleVoting.dtos.VoteDTO;
import com.api.scheduleVoting.dtos.VotingDTO;
import com.api.scheduleVoting.dtos.VotingSessionDTO;
import com.api.scheduleVoting.dtos.VotingSessionOpenDTO;
import com.api.scheduleVoting.entity.ScheduleEntity;
import com.api.scheduleVoting.entity.VotingEntity;
import com.api.scheduleVoting.entity.VotingSessionEntity;
import com.api.scheduleVoting.repository.VotingRepository;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VotingSessionServiceTest extends BaseTest {

    @Autowired
    private VotingSessionService service;

    @Autowired
    private VotingService votingService;

    @MockBean
    private ValidCPFClient validCPFClient;

    @MockBean
    private ScheduleService scheduleService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private VotingRepository votingRepository;

    @MockBean
    private AssociatedService associatedService;

    private static final Integer ID = 1;

    @Test
    public void testOpenVotingSession() {
        when(scheduleService.isScheduleId(any())).thenReturn(Boolean.TRUE);

        VotingSessionEntity response = service.openVotingSession(VotingSessionOpenDTO.builder()
                .scheduleId(ID)
                .time(1)
                .build());

        assertThat(response).isNotNull();
    }

    @Test
    public void testSearchDataResultVoting() {
        when(scheduleService.isScheduleId(any())).thenReturn(Boolean.TRUE);

        VotingSessionEntity votingSessionEntity = service.openVotingSession(VotingSessionOpenDTO.builder()
                .scheduleId(ID)
                .time(1)
                .build());

        when(scheduleService.isScheduleId(any())).thenReturn(Boolean.TRUE);
        when(validCPFClient.isVerifiesAssociatedEnabledVoting(any())).thenReturn(Boolean.TRUE);
        when(associatedService.isValidVotingMemberParticipation(any(), any())).thenReturn(Boolean.TRUE);
        when(associatedService.isAssociatedCanVote(any())).thenReturn(Boolean.TRUE);

        votingService.vote(VoteDTO.builder()
                .associatedCpf("03577834099")
                .scheduleId(1)
                .voting(Boolean.TRUE)
                .votingSessionId(1)
                .build());

        service.closeVotingSession(modelMapper.map(votingSessionEntity, VotingSessionDTO.class));

        when(votingRepository.findByVotingSessionId(any())).thenReturn(Collections.singletonList(VotingEntity.builder()
                .id(1)
                .vote(Boolean.TRUE)
                .schedule(ScheduleEntity.builder()
                        .id(1)
                        .build())
                .votingSession(VotingSessionEntity.builder()
                        .id(2)
                        .build())
                .build()));

        VotingDTO response = service.searchDataResultVoting(2);

        assertThat(response).isNotNull();
    }

}