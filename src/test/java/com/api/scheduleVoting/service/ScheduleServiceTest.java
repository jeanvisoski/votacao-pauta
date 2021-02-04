package com.api.scheduleVoting.service;

import com.api.scheduleVoting.BaseTest;
import com.api.scheduleVoting.client.ValidCPFClient;
import com.api.scheduleVoting.dtos.ScheduleDTO;
import com.api.scheduleVoting.entity.ScheduleEntity;
import com.api.scheduleVoting.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleServiceTest extends BaseTest {

    @Autowired
    private ScheduleService service;

    @MockBean
    private ValidCPFClient validCPFClient;

    private static final String DESCRICAO = "PRIMEIRA PAUTA TESTE";
    private static final Integer ID = 1;

    @Test
    public void testSaveSchedule() {
        ScheduleEntity request = ScheduleEntity.builder()
                .id(ID)
                .description(DESCRICAO)
                .build();

        ScheduleEntity response = service.save(ScheduleDTO.builder()
                .id(ID)
                .description(DESCRICAO)
                .build());

        assertThat(response).isEqualTo(request);
    }

    @Test
    public void testSearchScheduleByIdSchedule() {
        ScheduleEntity request = ScheduleEntity.builder()
                .id(ID)
                .description(DESCRICAO)
                .build();

        service.save(ScheduleDTO.builder()
                .id(ID)
                .description(DESCRICAO)
                .build());

        ScheduleEntity response = service.searchScheduleById(1);

        assertThat(response).isEqualTo(request);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundIdSchedule() {
        service.searchScheduleById(1);
    }

    @Test(expected = NotFoundException.class)
    public void testIsScheduleIdNotFound() {
        service.isScheduleId(10);
    }
}