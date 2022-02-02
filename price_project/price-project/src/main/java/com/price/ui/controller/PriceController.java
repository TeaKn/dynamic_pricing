package com.price.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.price.io.entity.VenueEntity;
import com.price.io.entity.WeatherEntity;
import com.price.io.repositories.VenueRepository;
import com.price.io.repositories.WeatherRepository;
import com.price.service.PriceService;
import com.price.service.WeatherService;
import com.price.service.client.BQClient;
import com.price.service.client.WeatherClient;
import com.price.shared.dto.ForecastDemand;
import com.price.shared.dto.TicketDTO;
import com.price.shared.dto.TicketPrice;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private PriceService priceService;
    private Flux<ForecastFlux> forecastFlux;

    // LOCATION

    @GetMapping(path = "/getGeolocationString/{location}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Geolocation> geolocationFlux(@PathVariable String location) {
        Flux<Geolocation> response = weatherClient.getGeolocationFlux(location);
        log.info("whatever: {}", response);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(response);
            log.info("json: {}", json);
        } catch (Exception e) {
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
        return returnValue;
    }

    // FORECAST

    @GetMapping(path = "/getForecast/{geo_id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ForecastFlux> getForecast(@PathVariable("geo_id") String geo_id) {
        return weatherClient.getForecast(geo_id);
    }

    // persist weather forecast into db
    @GetMapping(path = "/saveForecast/{geo_id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveForecast(@PathVariable("geo_id") String geo_id) {
        Flux<ForecastFlux> weatherRes = weatherClient.getForecast(geo_id);

        // nimam po kraju map, zdj dela sam za airolo ki je mono

        ModelMapper modelMapper = new ModelMapper();

        Flux.just(weatherRes)
                .map(forecastFlux -> modelMapper.map(forecastFlux, ForecastFlux.class))
                .map(ForecastFlux::getForecast)
                .map(Forecast::getDay)
                .map(day -> modelMapper.map(day, WeatherEntity.class))
                .flatMap(weatherRepository::save)
                .thenMany(weatherRepository.findAll())
                .subscribe(i -> log.info("saving " + i.toString()));


        //WeatherRest returnValue = new WeatherRest();
        //ModelMapper modelMapper = new ModelMapper();
        //WeatherDTO weatherDTO = modelMapper.map(weatherProg, WeatherDTO.class);
        //WeatherDTO savedWeather = weatherService.saveWeatherPredictionWeek(weatherDTO);
        //returnValue = modelMapper.map(savedWeather, WeatherRest.class);

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
    public List<ForecastDemand> explainArimaForecast() throws IOException {
        return bqClient.explainForecast();
    }

    // TICKET REQUEST

    @PostMapping(path = "/tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Flux<TicketPrice>> ticketRequest(@RequestBody TicketRequestModel ticketDetails) throws ParseException {

        ModelMapper modelMapper = new ModelMapper();
        TicketDTO ticketDTO = modelMapper.map(ticketDetails, TicketDTO.class);

        // parse input
        String location = ticketDetails.getVenue();

        // if datum in tti
        Date dateEnd = new SimpleDateFormat("dd.mm.yyyy").parse(ticketDetails.getEnd_time());
        Date dateStart = new SimpleDateFormat("dd.mm.yyyy").parse(ticketDetails.getStart_time());
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);

        c.add(Calendar.HOUR, 7 * 24);
        Date maxDate = c.getTime();
        if (dateStart.after(now) && dateStart.before(dateEnd) && dateEnd.before(maxDate)) {
            Flux<ForecastFlux> weatherForecast = weatherClient.getForecast(location);

        }

        return venueRepository.findByVenueName(location).map(venue ->
                priceService.getPrices(venue)
        );
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
