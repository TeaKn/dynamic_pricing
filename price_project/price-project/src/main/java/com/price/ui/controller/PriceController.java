package com.price.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.price.service.WeatherClient;
import com.price.ui.model.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: CALL WEATHER API

@RestController
@RequestMapping("/dynamic-price")
@Slf4j
public class PriceController {

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private Geolocation geolocation;

    @GetMapping(path = "/getGeolocationString/{location}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> geolocationString(@PathVariable String location) {
        Mono<String> response = weatherClient.getGeolocationString(location);
        log.info("whatever: {}", response);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(response);
            log.info("json: {}", json);
        } catch ( Exception e) {
            log.error("", e);
        }

        return response;
    }

    // LOCATION

    @GetMapping(path = "/getGeolocation/{location}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Geolocation[]> getGeolocation(@PathVariable String location) {
        return weatherClient.getGeolocation(location);
    }

    @GetMapping(path = "/getGeolocationFlux/{location}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Geolocation> getGeolocationFlux(@PathVariable String location) {
        Flux<Geolocation> returnValue = weatherClient.getGeolocationFlux(location);
        log.info("");
        return returnValue;
    }

    @GetMapping(path = "/getLocationString/{location}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getLocationString(@PathVariable String location) {
        return weatherClient.getGeolocationString(location);
    }

    // FORECAST

    @GetMapping(path = "/getForecast/{geo_id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> getForecast(@PathVariable("geo_id") String geo_id) {
        return weatherClient.getForecast(geo_id);
    }

}
