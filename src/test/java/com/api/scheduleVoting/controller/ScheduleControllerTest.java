package com.api.scheduleVoting.controller;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.entity.ScheduleEntity;
import com.api.scheduleVoting.service.ScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleControllerTest extends BaseTest {

    @MockBean
    private ScheduleService service;

    @Test
    public void testCreateSchedule() throws Exception {

        when(service.save(any())).thenReturn(ScheduleEntity.builder()
                .id(1)
                .descricao("PRIMEIRA PAUTA TESTE")
                .build());

        mockMvc.perform(post("/api/v1/pauta")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonAsString("json/scheduleRequest.json")))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().json(jsonAsString("json/scheduleResponse.json")));
    }

    @Test
    public void testSearchSchedule() throws Exception {

        when(service.searchScheduleById(any())).thenReturn(ScheduleEntity.builder()
                .id(1)
                .descricao("PRIMEIRA PAUTA TESTE")
                .build());

        mockMvc.perform(get("/api/v1/pauta/1")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(jsonAsString("json/scheduleResponse.json")));
    }

}