package com.price.service;

import com.price.ui.model.response.Forecast;
import com.price.ui.model.response.ForecastFlux;
import com.price.ui.model.response.Geolocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
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

    public Flux<Geolocation> getGeolocationFlux(String location) {

        return webClient.get()
                .uri("/srf-meteo/geolocationNames?name={location}", location)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Geolocation.class));

    }

    // FORECAST

    public Flux<ForecastFlux> getForecast(String location) {

        // fix the encoding of the uri, uri gets truncated because of "."
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
                    .bodyToMono(ForecastFlux.class);

        });
    }
}
