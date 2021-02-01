package com.api.scheduleVoting.client;

import com.api.scheduleVoting.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class ValidCPFClient {

    private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";

    @Value("${url}")
    private String URL;

    public boolean isVerifiesAssociatedEnabledVoting(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = URL;
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class, cpf);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode status = root.path("status");

            if (ABLE_TO_VOTE.equals(status.textValue())) {
                return Boolean.TRUE;
            }

            return Boolean.FALSE;

        } catch (HttpClientErrorException | IOException ex) {
            throw new NotFoundException("Não foi possivel localizar o CPF do associado");
        }
    }
}
