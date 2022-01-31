package com.price.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.price.io.entity.VenueEntity;
import com.price.io.repositories.VenueRepository;
import com.price.service.TicketService;
import com.price.service.client.BQClient;
import com.price.service.client.WeatherClient;
import com.price.shared.dto.TicketDTO;
import com.price.ui.model.request.TicketRequestModel;
import com.price.ui.model.response.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    // now i access it here, čeprav to je narobe
   @Autowired
    private VenueRepository venueRepository;

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
    public Flux<ForecastFlux> getForecast(@PathVariable("geo_id") String geo_id) {
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

    @GetMapping(path = "bq/explainForecast")
    public String explainArimaForecast() throws IOException {
        return bqClient.explainForecast();
    }

    // TICKET REQUEST

    @PostMapping(path = "/tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PriceResponseModel ticketRequest(@RequestBody TicketRequestModel ticketDetails) {

        ModelMapper modelMapper = new ModelMapper();
        TicketDTO ticketDTO = modelMapper.map(ticketDetails, TicketDTO.class);


        venueRepository.findById(1L).subscribe(v->System.out.println("Venue id: " + v.toString()));

        //TicketDTO createdTicket = ticketService.createTicket(ticketDTO);
        //PriceResponseModel returnValue = modelMapper.map(createdTicket, PriceResponseModel.class);

        return new PriceResponseModel();
    }

    // return list of venues
    @GetMapping(path = "/allVenues")
    public Flux<VenueEntity> getVenuesList() {
        return venueRepository.findAll();
    }

    // save a venue
    @PostMapping(path = "addVenue")
    public Mono<VenueEntity> saveVenue(@RequestBody VenueEntity venueEntity) {
        return venueRepository.save(venueEntity);
    }

    // TODO: ticket request, get weather for the day and get venue details
}
