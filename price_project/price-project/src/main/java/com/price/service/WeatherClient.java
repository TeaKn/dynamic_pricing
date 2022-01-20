package com.price.service;

import com.price.ui.model.response.Geolocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;


@Service
public class WeatherClient {


    private Geolocation geolocation;

    @Autowired
    private WebClient webClient;

    // LOCATION


    public Mono<Geolocation[]> getGeolocation(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .retrieve()
                .bodyToMono(Geolocation[].class);

    }
    public Mono<String> getGeolocationString(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));

    }

    public Flux<Geolocation> getGeolocationFlux(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Geolocation.class));

    }

    // FORECAST


    public Mono<String> getForecastTest(String location) {

        String geo_id = geolocation.getGeolocation().getId();
        return webClient.get()
                .uri("/srf-meteo/forecast/{location}", location)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Flux<String> getForecast(String location) {

        return this.getGeolocationFlux(location).flatMap((Geolocation geolocation1) -> {
            this.geolocation = geolocation1;
            String geo_id = geolocation1.getGeolocation().getId();

            String baseUrl = "https://api.srgssr.ch";
            DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
            uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
            URI uri = uriBuilderFactory.uriString("/srf-meteo/forecast/{geo_id}")
                    .build(geo_id);

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class);

        });
    }
}
