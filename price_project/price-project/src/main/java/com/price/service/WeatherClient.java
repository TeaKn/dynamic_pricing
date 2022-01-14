package com.price.service;

import com.price.ui.model.response.AccessToken;
import com.price.ui.model.response.PriceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class WeatherClient {

    private static final String SRGSSR_API_BASE_URL = "https://api.srgssr.ch";
    private AccessToken accessToken;
    private static final String USERNAME = "vhCOppbnnNPlnlonuRqXqIXOzWs9SZKE";
    private static final String PASSWORD = "R79BNPhRB6IGNN07";

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
    public Mono<AccessToken> getAccessToken() {
        return webClient.get()
                .uri("/oauth/v1/accesstoken?grant_type=client_credentials")
                .header(HttpHeaders.CONTENT_LENGTH, "0")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils
                        .encodeToString((USERNAME + ":" + PASSWORD).getBytes(UTF_8)))
                .retrieve()
                .bodyToMono(AccessToken.class);
        //ACCESS_TOKEN = retrievedResource.map(AccessToken::getAccess_token);


    }
    private Function<String, ClientRequest> setBearerTokenInHeader(ClientRequest request) {
        return token -> ClientRequest.from(request).header("Bearer ", token).build();
    }


    public Mono<Object> getGeolocation(String location) {

        if (this.accessToken == null) {

            return this.getAccessToken().map((AccessToken accessToken1) -> {
                this.accessToken = accessToken1;
                return webClient.get()
                        .uri("/srf-meteo/geolocationNames?name={location}", location)
                        //.header(HttpHeaders.AUTHORIZATION, "Bearer ")
                        .headers(h -> h.setBearerAuth(accessToken1.getAccess_token()))
                        .retrieve()
                        .bodyToMono(String.class);

            });
        }
        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                //.header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .headers(h -> h.setBearerAuth(this.accessToken.getAccess_token()))
                .retrieve()
                .bodyToMono(Object.class);

    }

    public Mono<Object> getForecast(String geolocation) {

        if (this.accessToken == null) {

            return this.getAccessToken().map((AccessToken accessToken1) -> {
                this.accessToken = accessToken1;
                return webClient.get()
                        .uri("/srf-meteo/forecast/{geolocation}", geolocation)
                        .headers(h -> h.setBearerAuth(accessToken1.getAccess_token()))
                        .retrieve()
                        .bodyToMono(String.class);

            });
        }
        return webClient.get()
                .uri("/srf-meteo/forecast/{geolocation}", geolocation)
                //.header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .headers(h -> h.setBearerAuth(this.accessToken.getAccess_token()))
                .retrieve()
                .bodyToMono(Object.class);
    }
}
