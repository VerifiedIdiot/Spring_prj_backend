package com.Doggo.DoggoEx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api.api-ninjas.com/v1")
                .defaultHeader("X-Api-Key", "JdEHVysNs99Ynxgn06i2dg==caKQZGXlvsyF9Xg4" )
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();

    }
}

