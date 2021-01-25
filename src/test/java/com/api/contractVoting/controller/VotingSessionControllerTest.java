package com.api.contractVoting.controller;

import com.api.contractVoting.dtos.VotingSessionDTO;
import com.api.contractVoting.dtos.VotingSessionOpenDTO;
import com.api.contractVoting.entity.ContractEntity;
import com.api.contractVoting.repository.ContractRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VotingSessionControllerTest {

    private static final String url = "/api/v1/votacao";
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ContractRepository contractRepository;

    private static final String UTC = "UTC-3";

    @Test
    public void newRegistrationSession() {
        ContractEntity pauta = new ContractEntity(null, "Teste Pauta 1");
        pauta = this.contractRepository.save(pauta);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

        VotingSessionOpenDTO votingSessionOpenDTO = new VotingSessionOpenDTO(pauta.getId(), null);

        ResponseEntity<VotingSessionDTO> responseEntity = restTemplate.postForEntity(url.concat("/abrir-sessao"), votingSessionOpenDTO, VotingSessionDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getActive()).isTrue();
        assertThat(responseEntity.getBody().getDateTimeStart().format(formatter)).isEqualTo(LocalDateTime.now(ZoneId.of(UTC)).format(formatter));
        assertThat(responseEntity.getBody().getDateTimeEnd().format(formatter)).isEqualTo(LocalDateTime.now(ZoneId.of(UTC)).plusMinutes(1).format(formatter));
    }

    @Test
    public void newRegistrationSessionWithTime10() {
        ContractEntity pauta = new ContractEntity(null, "Teste Pauta 1");
        pauta = this.contractRepository.save(pauta);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

        VotingSessionOpenDTO votingSessionOpenDTO = new VotingSessionOpenDTO(pauta.getId(), 10);

        ResponseEntity<VotingSessionDTO> responseEntity = restTemplate.postForEntity(url.concat("/abrir-sessao"), votingSessionOpenDTO, VotingSessionDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getActive()).isTrue();
        assertThat(responseEntity.getBody().getDateTimeStart().format(formatter)).isEqualTo(LocalDateTime.now(ZoneId.of(UTC)).format(formatter));
        assertThat(responseEntity.getBody().getDateTimeEnd().format(formatter)).isEqualTo(LocalDateTime.now(ZoneId.of(UTC)).plusMinutes(10).format(formatter));
    }

    @Test
    public void registerNewSessionOfIdInvalid() {
        VotingSessionOpenDTO votingSessionOpenDTO = new VotingSessionOpenDTO(10, null);

        ResponseEntity<VotingSessionDTO> responseEntity = restTemplate.postForEntity(url.concat("/abrir-sessao"), votingSessionOpenDTO, VotingSessionDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void registerNewNewSessionOfContractInvalid() {
        VotingSessionOpenDTO votingSessionOpenDTO = new VotingSessionOpenDTO(null, null);

        ResponseEntity<VotingSessionDTO> responseEntity = restTemplate.postForEntity(url.concat("/abrir-sessao"), votingSessionOpenDTO, VotingSessionDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
