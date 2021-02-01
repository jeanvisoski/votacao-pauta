package com.api.scheduleVoting.controller;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.entity.VotingSessionEntity;
import com.api.scheduleVoting.service.VotingSessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
@SpringBootTest
@AutoConfigureMockMvc
public class VotingSessionControllerTest extends BaseTest {

    @MockBean
    private VotingSessionService service;

    @Test
    public void testCreateNewSession() throws Exception {
        when(service.openVotingSession(any())).thenReturn(VotingSessionEntity.builder()
                .id(1)
                .dateTimeStart(LocalDateTime.now())
                .dateTimeEnd(LocalDateTime.now())
                .active(true)
                .build());

        mockMvc.perform(post("/api/v1/sessao")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonAsString("json/sessionRequest.json")))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }
}