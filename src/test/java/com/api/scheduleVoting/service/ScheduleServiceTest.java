package com.api.scheduleVoting.service;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.client.ValidCPFClient;
import com.api.scheduleVoting.dtos.ScheduleDTO;
import com.api.scheduleVoting.entity.ScheduleEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleServiceTest extends BaseTest {

    @MockBean
    private ScheduleService service;

    @MockBean
    private ValidCPFClient validCPFClient;

    private static final String DESCRICAO = "PRIMEIRA PAUTA TESTE";
    private static final Integer ID = 1;

    @Test
    public void testSaveSchedule() throws Exception {

        when(service.save(any())).thenReturn(ScheduleEntity.builder()
                .id(ID)
                .descricao(DESCRICAO)
                .build());

        ScheduleEntity response = service.save(ScheduleDTO.builder()
                .id(ID)
                .descricao(DESCRICAO)
                .build());

        assertThat(response).isEqualTo(ScheduleEntity.builder()
                .id(ID)
                .descricao(DESCRICAO)
                .build());

    }

    @Test
    public void testSearchScheduleByIdSchedule() throws Exception {

        when(service.searchScheduleById(any())).thenReturn(ScheduleEntity.builder()
                .id(ID)
                .descricao(DESCRICAO)
                .build());

        ScheduleEntity response = service.searchScheduleById(1);

        assertThat(response).isEqualTo(ScheduleEntity.builder()
                .id(ID)
                .descricao(DESCRICAO)
                .build());

    }

}