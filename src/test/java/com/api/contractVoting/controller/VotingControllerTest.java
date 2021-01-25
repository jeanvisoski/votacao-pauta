package com.api.contractVoting.controller;

import com.api.contractVoting.dtos.ResultDTO;
import com.api.contractVoting.dtos.VoteDTO;
import com.api.contractVoting.entity.AssociatedEntity;
import com.api.contractVoting.entity.ContractEntity;
import com.api.contractVoting.entity.VotingEntity;
import com.api.contractVoting.entity.VotingSessionEntity;
import com.api.contractVoting.repository.AssociatedRepository;
import com.api.contractVoting.repository.ContractRepository;
import com.api.contractVoting.repository.VotingRepository;
import com.api.contractVoting.repository.VotingSessionRepository;
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
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VotingControllerTest {

    private static final String url = "/api/v1/votacoes";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private VotingRepository votingRepository;
    @Autowired
    private VotingSessionRepository votingSessionRepository;
    @Autowired
    private AssociatedRepository associatedRepository;
    @Autowired
    private ContractRepository contractRepository;

    private static final String UTC = "UTC-3";

    @Test
    public void voteWithSuccess() {

        this.contractRepository.deleteAll();
        this.votingSessionRepository.deleteAll();
        this.associatedRepository.deleteAll();

        ContractEntity pauta = new ContractEntity(null, "Teste Pauta 1");
        pauta = this.contractRepository.save(pauta);

        VotingSessionEntity votingSessionEntity = new VotingSessionEntity(null, LocalDateTime.now(ZoneId.of(UTC)), LocalDateTime.now(ZoneId.of(UTC)).plusMinutes(1), Boolean.TRUE);
        votingSessionEntity = this.votingSessionRepository.save(votingSessionEntity);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.concat("/votar"),
                new VoteDTO(pauta.getId(),
                        votingSessionEntity.getId(),
                        Boolean.TRUE, "03577834099"),
                String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo("Voto registrado com sucesso!");
    }

    @Test
    public void resultVotingWithSuccess() {
        ContractEntity contractEntity = new ContractEntity(null, "Teste Pauta 1");
        contractEntity = this.contractRepository.save(contractEntity);

        VotingSessionEntity votingSessionEntity = new VotingSessionEntity(null, LocalDateTime.now(ZoneId.of(UTC)), LocalDateTime.now(ZoneId.of(UTC)).plusMinutes(1), Boolean.TRUE);
        votingSessionEntity = this.votingSessionRepository.save(votingSessionEntity);

        votingRepository.save(new VotingEntity(null, Boolean.TRUE, contractEntity.getId(), votingSessionEntity.getId()));
        votingRepository.save(new VotingEntity(null, Boolean.TRUE, contractEntity.getId(), votingSessionEntity.getId()));
        votingRepository.save(new VotingEntity(null, Boolean.TRUE, contractEntity.getId(), votingSessionEntity.getId()));

        votingSessionEntity = new VotingSessionEntity(votingSessionEntity.getId(), votingSessionEntity.getDateTimeStart(), votingSessionEntity.getDateTimeEnd(), Boolean.FALSE);
        this.votingSessionRepository.save(votingSessionEntity);

        ResponseEntity<ResultDTO> responseEntity = restTemplate.getForEntity(url.concat("/resultado/{contractId}/{votingSessionId}"),
                ResultDTO.class,
                contractEntity.getId(),
                votingSessionEntity.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getVotingDTO().getVotingSessionId()).isEqualTo(votingSessionEntity.getId());
        assertThat(responseEntity.getBody().getVotingDTO().getQuantityVoteYes()).isEqualTo(3);
        assertThat(responseEntity.getBody().getVotingDTO().getQuantityVoteNo()).isEqualTo(0);
    }

    @Test
    public void voteComCpfInvalid() {
        ContractEntity contractEntity = new ContractEntity(null, "Teste Pauta 1");
        contractEntity = this.contractRepository.save(contractEntity);

        VotingSessionEntity votingSessionEntity = new VotingSessionEntity(null, LocalDateTime.now(ZoneId.of(UTC)), LocalDateTime.now(ZoneId.of(UTC)).plusMinutes(1), Boolean.TRUE);
        votingSessionEntity = this.votingSessionRepository.save(votingSessionEntity);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.concat("/votar"),
                new VoteDTO(contractEntity.getId(),
                        votingSessionEntity.getId(),
                        Boolean.TRUE, "123"),
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void voteWhenSessionWasClosed() {
        ContractEntity contractEntity = new ContractEntity(null, "Teste Pauta 1");
        contractEntity = this.contractRepository.save(contractEntity);

        VotingSessionEntity votingSessionEntity = new VotingSessionEntity(null, LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE);
        votingSessionEntity = this.votingSessionRepository.save(votingSessionEntity);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.concat("/votar"),
                new VoteDTO(contractEntity.getId(),
                        votingSessionEntity.getId(),
                        Boolean.TRUE,
                        "03577834099"),
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
    }

    @Test
    public void voteWhenAssociatedAlreadyVoted() {
        ContractEntity contractEntity = new ContractEntity(null, "Teste Pauta 1");
        contractEntity = this.contractRepository.save(contractEntity);

        VotingSessionEntity votingSessionEntity = new VotingSessionEntity(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.TRUE);
        votingSessionEntity = this.votingSessionRepository.save(votingSessionEntity);

        AssociatedEntity associatedEntity = new AssociatedEntity(null, "03577834099", contractEntity.getId());
        associatedRepository.save(associatedEntity);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.concat("/votar"),
                new VoteDTO(contractEntity.getId(),
                        votingSessionEntity.getId(),
                        Boolean.TRUE,
                        "03577834099"),
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
