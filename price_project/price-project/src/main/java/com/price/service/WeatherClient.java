package com.price.service;

import com.price.ui.model.response.PriceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class WeatherClient {

    private static final String SRGSSR_API_BASE_URL = "https://api.srgssr.ch";
    private String ACCESS_TOKEN;

    private final WebClient webClient;

    // creating an instance of WebClient

    @Autowired
    public WeatherClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(SRGSSR_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                //.filter(ExchangeFilterFunctions.basicAuthentication(user, password))
                .build();
    }

    // get access token with get request
    public Mono<PriceRest> getAccessToken() {
        return webClient.get()
                .uri("/oauth/v1/accesstoken?grant_type=client_credentials")
                .header(HttpHeaders.CONTENT_LENGTH, "0")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils
                        .encodeToString(("vhCOppbnnNPlnlonuRqXqIXOzWs9SZKE" + ":" + "R79BNPhRB6IGNN07").getBytes(UTF_8)))
                .retrieve()
                .bodyToMono(PriceRest.class);
    }

    public Mono<PriceRest> getGeolocation() {
        return webClient.get()
                .uri("/srf-meteo/geolocationNames/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .retrieve()
                .bodyToMono(PriceRest.class);
    }

    public Mono<PriceRest> getForecast() {
        return webClient.get()
                .uri("/srf-meteo/forecast/46.5292,8.6081")//for now it is specific, just testing
                .retrieve()
                .bodyToMono(PriceRest.class);
    }
}
