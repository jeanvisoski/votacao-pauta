package com.api.scheduleVoting.service;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.client.ValidCPFClient;
import com.api.scheduleVoting.dtos.VoteDTO;
import com.api.scheduleVoting.exception.InvalidVoteException;
import com.api.scheduleVoting.exception.NotFoundException;
import com.api.scheduleVoting.exception.SessionFindException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VotingServiceTest extends BaseTest {

    @Autowired
    private VotingService service;

    @MockBean
    private ValidCPFClient validCPFClient;

    @MockBean
    private VotingSessionService votingSessionService;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private AssociatedService associatedService;

    @Test
    public void testVotingSuccess() {
        VoteDTO request = VoteDTO.builder()
                .associatedCpf("03577834099")
                .scheduleId(1)
                .voting(Boolean.TRUE)
                .votingSessionId(1)
                .build();

        when(scheduleService.isScheduleId(any())).thenReturn(Boolean.TRUE);
        when(votingSessionService.isValidVoteSession(any())).thenReturn(Boolean.TRUE);
        when(validCPFClient.isVerifiesAssociatedEnabledVoting(any())).thenReturn(Boolean.TRUE);
        when(associatedService.isValidVotingMemberParticipation(any(), any())).thenReturn(Boolean.TRUE);
        when(associatedService.isAssociatedCanVote(any())).thenReturn(Boolean.TRUE);

        String response = service.vote(request);

        assertThat(response).isEqualTo("Voto registrado com sucesso!");
    }

    @Test(expected = InvalidVoteException.class)
    public void testVotingWithInvalidCpf() {
        VoteDTO request = VoteDTO.builder()
                .associatedCpf("12345678910")
                .scheduleId(1)
                .voting(Boolean.TRUE)
                .votingSessionId(1)
                .build();

        when(scheduleService.isScheduleId(any())).thenReturn(Boolean.TRUE);
        when(votingSessionService.isValidVoteSession(any())).thenReturn(Boolean.TRUE);
        when(validCPFClient.isVerifiesAssociatedEnabledVoting(any())).thenReturn(Boolean.FALSE);

        service.vote(request);
    }

    @Test(expected = NotFoundException.class)
    public void testVotingWithInvalidSchedule() {
        VoteDTO request = VoteDTO.builder()
                .associatedCpf("03577834099")
                .scheduleId(123)
                .voting(Boolean.TRUE)
                .votingSessionId(1)
                .build();

        when(scheduleService.isScheduleId(any())).thenReturn(Boolean.FALSE);
        when(votingSessionService.isValidVoteSession(any())).thenReturn(Boolean.TRUE);
        when(validCPFClient.isVerifiesAssociatedEnabledVoting(any())).thenReturn(Boolean.TRUE);

        service.vote(request);
    }

    @Test(expected = SessionFindException.class)
    public void testVotingSessionWaxed() {
        VoteDTO request = VoteDTO.builder()
                .associatedCpf("03577834099")
                .scheduleId(123)
                .voting(Boolean.TRUE)
                .votingSessionId(1)
                .build();

        when(scheduleService.isScheduleId(any())).thenReturn(Boolean.TRUE);
        when(votingSessionService.isValidVoteSession(any())).thenReturn(Boolean.TRUE);
        when(validCPFClient.isVerifiesAssociatedEnabledVoting(any())).thenReturn(Boolean.TRUE);
        when(votingSessionService.isValidVoteSession(any())).thenReturn(Boolean.FALSE);

        service.vote(request);
    }

    @Test(expected = InvalidVoteException.class)
    public void testValidVotingMemberParticipation() {
        VoteDTO request = VoteDTO.builder()
                .associatedCpf("03577834099")
                .scheduleId(123)
                .voting(Boolean.TRUE)
                .votingSessionId(1)
                .build();

        when(scheduleService.isScheduleId(any())).thenReturn(Boolean.TRUE);
        when(votingSessionService.isValidVoteSession(any())).thenReturn(Boolean.TRUE);
        when(validCPFClient.isVerifiesAssociatedEnabledVoting(any())).thenReturn(Boolean.TRUE);
        when(votingSessionService.isValidVoteSession(any())).thenReturn(Boolean.TRUE);
        when(associatedService.isValidVotingMemberParticipation(any(), any())).thenReturn(Boolean.FALSE);

        service.vote(request);
    }
}