package com.price.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.api.services.bigquery.model.Model;
import com.price.service.BQClient;
import com.price.service.WeatherClient;
import com.price.ui.model.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;


@RestController
@RequestMapping("/dynamic-price")
@Slf4j
public class PriceController {

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private Geolocation geolocation;

    @Autowired
    private BQClient bqClient;

    // LOCATION

    @GetMapping(path = "/getGeolocationString/{location}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Geolocation> geolocationFlux(@PathVariable String location) {
        Flux<Geolocation> response = weatherClient.getGeolocationFlux(location);
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

    // FORECAST

    @GetMapping(path = "/getForecast/{geo_id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> getForecast(@PathVariable("geo_id") String geo_id) {
        return weatherClient.getForecast(geo_id);
    }

    // BQ

    @GetMapping(path = "/bq/getNormalizedDataWithCoeff")
    public String getNormDataWithCoeff() throws IOException {
        return bqClient.normalizedDataWithCorrelationCoeff();
    }

    @GetMapping(path = "bq/arimaModel/{projectId}/{datasetId}/{modelId}")
    public Mono<String> getArimaModel(@PathVariable String projectId, @PathVariable String datasetId, @PathVariable String modelId) {
        return bqClient.getArimaModel(projectId, datasetId, modelId);
    }

    @GetMapping(path = "/bq/createArimaModel")
    public String getCreateArimaModel() throws IOException {
        return bqClient.ArimaModel();
    }


}
