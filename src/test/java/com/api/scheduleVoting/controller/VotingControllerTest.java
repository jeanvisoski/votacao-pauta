package com.api.scheduleVoting.controller;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.client.ValidCPFClient;
import com.api.scheduleVoting.entity.VotingEntity;
import com.api.scheduleVoting.entity.VotingSessionEntity;
import com.api.scheduleVoting.service.VotingService;
import com.api.scheduleVoting.service.VotingSessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
@SpringBootTest
@AutoConfigureMockMvc
public class VotingControllerTest extends BaseTest {

    @MockBean
    private VotingService service;

    @MockBean
    private ValidCPFClient validCPFClient;

    @Test
    public void testVoteSuccess() throws Exception {

        when(service.vote(any())).thenReturn("Voto registrado com sucesso!");

        mockMvc.perform(post("/api/v1/votacao")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonAsString("json/voteRequest.json")))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string("Voto registrado com sucesso!"));
    }

}