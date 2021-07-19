package com.example.workflow.client;

import com.example.workflow.dto.BarberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class BarberShopClient {

    private static final Logger LOG = LoggerFactory.getLogger(BarberShopClient.class);

    @Value("${barbearia.url}")
    protected String url;

    private final RestTemplate restTemplate;

    public BarberShopClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BarberDTO> verifyOnline() {
        final LocalDateTime inicioChamada = LocalDateTime.now();
        LOG.debug("Iniciando chamada do Barbearia -> verificaOnline");

        final String urlSend = url +"barbearia/verificaOnline";
        final ResponseEntity<BarberDTO[]> result = this.restTemplate.getForEntity(url, BarberDTO[].class);

        LOG.debug("Finalizando a chamada do barbearia/verificaOnline, statusCode [{}], em [{}] miliseconds", result.getStatusCodeValue(), Duration.between(inicioChamada, LocalDateTime.now()).toMillis());
        return Arrays.asList(Objects.requireNonNull(result.getBody()));
    }

    public BarberDTO greaterRelationship() {
        final LocalDateTime inicioChamada = LocalDateTime.now();
        LOG.debug("Iniciando chamada do Barbearia -> maiorRelacionamento");

        final String urlSend = url +"barbearia/maiorRelacionamento";
        final ResponseEntity<BarberDTO> result = this.restTemplate.getForEntity(url, BarberDTO.class);

        LOG.debug("Finalizando a chamada do barbearia/maiorRelacionamento, statusCode [{}], em [{}] miliseconds", result.getStatusCodeValue(), Duration.between(inicioChamada, LocalDateTime.now()).toMillis());
        return result.getBody();
    }


}
