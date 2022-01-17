package com.price.service;

import com.price.ui.model.response.AccessToken;
import com.price.ui.model.response.Geolocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
public class WeatherClient {

    private static final String SRGSSR_API_BASE_URL = "https://api.srgssr.ch";
    private AccessToken accessToken;
    private Geolocation geolocation;
    private static final String USERNAME = "vhCOppbnnNPlnlonuRqXqIXOzWs9SZKE";
    private static final String PASSWORD = "R79BNPhRB6IGNN07";

    private final WebClient webClient;


    // creating an instance of WebClient

    @Autowired
    public WeatherClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(SRGSSR_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                //.filter(ExchangeFilterFunctions.basicAuthentication(USERNAME, PASSWORD))
                //.defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils
                //       .encodeToString((USERNAME + ":" + PASSWORD).getBytes(UTF_8)))
                .build();
    }

    // get access token with get request
    public Mono<AccessToken> getAccessToken() {
        return webClient.mutate()
                .filters(filterList -> {
                    filterList.add(0, basicAuthentication(USERNAME, PASSWORD));
                })
                .build()
                .get()
                .uri("/oauth/v1/accesstoken?grant_type=client_credentials")
                .header(HttpHeaders.CONTENT_LENGTH, "0")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                //.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils
                //        .encodeToString((USERNAME + ":" + PASSWORD).getBytes(UTF_8)))
                .retrieve()
                .bodyToMono(AccessToken.class);


    }

    public Mono<Geolocation[]> getGeolocation(String location) {

        if (this.accessToken == null) {

            return this.getAccessToken().flatMap((AccessToken accessToken1) -> {
                this.accessToken = accessToken1;
                return webClient.get()
                        .uri("/srf-meteo/geolocationNames?name={location}", location)
                        //.header(HttpHeaders.AUTHORIZATION, "Bearer ")
                        .headers(h -> h.setBearerAuth(accessToken1.getAccess_token()))
                        .retrieve()
                        .bodyToMono(Geolocation[].class);
            });
        }
        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                //.header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .headers(h -> h.setBearerAuth(this.accessToken.getAccess_token()))
                .retrieve()
                .bodyToMono(Geolocation[].class);

    }

    public Flux<Geolocation> geolocationFlux(String location) {
        if (this.accessToken == null) {

            return this.getAccessToken().flatMapMany((AccessToken accessToken1) -> {
                this.accessToken = accessToken1;
                return webClient.get()
                        .uri("/srf-meteo/geolocationNames?name={location}", location)
                        //.header(HttpHeaders.AUTHORIZATION, "Bearer ")
                        .headers(h -> h.setBearerAuth(accessToken1.getAccess_token()))
                        .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Geolocation.class));
            });
        }
        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                //.header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .headers(h -> h.setBearerAuth(this.accessToken.getAccess_token()))
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Geolocation.class));

    }

    public Mono<String> getForecast(String location) {

        if (this.accessToken == null) {
            return this.getAccessToken().flatMap((AccessToken accessToken1) -> {
                this.accessToken = accessToken1;
//                this.getGeolocation(location).flatMap((Geolocation geolocation1) -> {
//                    this.geolocation = geolocation1;
//                });
//                    String geo_id = geolocation1.getGeolocation().getId();
                        return webClient.get()
                            .uri("/srf-meteo/forecast/{location}", location)
                            .headers(h -> h.setBearerAuth(accessToken1.getAccess_token()))
                            .retrieve()
                            .bodyToMono(String.class);
                });
        }
        String geo_id = geolocation.getGeolocation().getId();
        return webClient.get()
                .uri("/srf-meteo/forecast/{geolocation}", geo_id)
                //.header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .headers(h -> h.setBearerAuth(this.accessToken.getAccess_token()))
                .retrieve()
                .bodyToMono(String.class);
    }
}
