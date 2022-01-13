package com.price.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class WeatherClient {

    private static final String SRGSSR_API_BASE_URL = "https://api.srgssr.ch";

    private final WebClient webClient;

    // creating an instance of WebClient

    @Autowired
    public WeatherClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(SRGSSR_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    // get access token with get request
    public Mono<String> getAccessToken() {
        return webClient.get()
                .uri("/oauth/v1/accesstoken?grant_type=client_credentials")
                .header(HttpHeaders.CONTENT_LENGTH, "0")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils
                        .encodeToString(("vhCOppbnnNPlnlonuRqXqIXOzWs9SZKE" + ":" + "R79BNPhRB6IGNN07").getBytes(UTF_8)))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getGeolocation() {
        return webClient.get()
                .uri("")
                .retrieve()
                .bodyToMono(String.class);
    }
}
