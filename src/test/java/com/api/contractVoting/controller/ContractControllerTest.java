package com.api.contractVoting.controller;

import com.api.contractVoting.dtos.ContractDTO;
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
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractControllerTest{

    private static final String URL = "/api/v1/pautas";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContractRepository repository;

    @Test
    public void newContract() {
        ResponseEntity<ContractDTO> responseEntity = restTemplate.postForEntity(URL, new ContractDTO(null, "Primeira PAUTA"), ContractDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void searchContract() {
        ContractEntity pauta = new ContractEntity(null, "Primeira PAUTA");
        repository.save(pauta);

        ResponseEntity<ContractDTO> responseEntity = restTemplate.getForEntity(URL.concat("/{id}"), ContractDTO.class, 1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getDescricao()).isEqualTo(pauta.getDescricao());
    }

    @Test
    public void newContractWithNullValueShouldReturnError() {
        ResponseEntity<ContractDTO> responseEntity = restTemplate.postForEntity(URL, new ContractDTO(null, null), ContractDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void newContractWithValueDoesNotExistShouldReturnError() {
        ContractEntity pauta = new ContractEntity(null, "Primeira PAUTA");
        repository.save(pauta);

        ResponseEntity<ContractDTO> responseEntity = restTemplate.getForEntity(URL.concat("/{id}"), ContractDTO.class, 2);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getDescricao()).isNotEqualTo(pauta.getDescricao());
    }

}
