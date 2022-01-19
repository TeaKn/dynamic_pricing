package com.price.service;

import com.price.ui.model.response.Geolocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class WeatherClient {


    private Geolocation geolocation;

    @Autowired
    private WebClient webClient;


    public Mono<Geolocation[]> getGeolocation(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .retrieve()
                .bodyToMono(Geolocation[].class);

    }
    public Mono<String> geolocationString(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));

    }

    public Flux<Geolocation> geolocationFlux(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Geolocation.class));

    }

    public Flux<Geolocation> getLocationFlux(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Geolocation.class));

    }

    public Mono<String> getForecastTest(String location) {

        return webClient.get()
                .uri("/srf-meteo/forecast/{location}", location)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getForecast(String location) {

        String geo_id = geolocation.getGeolocation().getId();
        return webClient.get()
                .uri("/srf-meteo/forecast/{location}", location)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getLocationString(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
    }
}
