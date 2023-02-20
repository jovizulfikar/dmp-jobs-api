package com.dmp.jobsapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.dmp.jobsapi.models.Job;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@Component
public class JobRepository {

    private WebClient webClient;

    JobRepository() {
        this.webClient = WebClient.builder()
            .codecs(configurer -> configurer.defaultCodecs().jackson2JsonDecoder(
            new Jackson2JsonDecoder(
                new ObjectMapper().setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
            ))).build();
    }

    public List<Job> findAll() {
        String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";
        ResponseEntity<List<Job>> response = webClient.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntityList(Job.class)
            .block();

        return response.getBody();
    }

    public Optional<Job> findById(String id) {
        String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions/" + id;
        ResponseEntity<Job> response = webClient.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(Job.class)
            .block();

        Job job = response.getBody();
        return Optional.ofNullable(job.getId() != null ? job : null);
    }
}
