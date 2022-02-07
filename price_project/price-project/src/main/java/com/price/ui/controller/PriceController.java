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
import java.util.ArrayList;
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
    public Flux<WeatherEntity> saveForecast(@PathVariable("geo_id") String geo_id) {
        Flux<ForecastFlux> weatherRes = weatherClient.getForecast(geo_id);

        // nimam po kraju map, zdj dela sam za airolo ki je mono

        ModelMapper modelMapper = new ModelMapper();

        return weatherRepository
                .deleteAll().thenMany(
                        weatherRes
                .map(ForecastFlux::getForecast)
                .map(Forecast::getDay)
                .map(days -> {
                    ArrayList<WeatherEntity> entityList = new ArrayList<>();

                    for (Day day:days) {
                        WeatherEntity entity = new WeatherEntity();
                        entity.setTN_C(day.getTN_C());
                        entity.setFF_KMH(day.getFF_KMH());
                        entity.setLocation(geo_id);
                        entity.setPROBPCP_PERCENT(day.getPROBPCP_PERCENT());
                        entity.setTX_C(day.getTX_C());
                        entity.setLocal_date_time(day.getLocal_date_time());
                        entity.setFX_KMH(day.getFX_KMH());
                        entityList.add(entity);
                    }
                    return entityList;
                })
                .flatMap(weatherEntities -> weatherRepository.saveAll(weatherEntities)));
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
    public Mono<Object> ticketRequest(@RequestBody TicketRequestModel ticketDetails) throws ParseException {

        //ModelMapper modelMapper = new ModelMapper();
        //TicketDTO ticketDTO = modelMapper.map(ticketDetails, TicketDTO.class);

        // parse input
        String location = ticketDetails.getVenue();

        // if datum in tti
        Date dateEnd = new SimpleDateFormat("dd.MM.yyyy").parse(ticketDetails.getEnd_time());
        Date dateStart = new SimpleDateFormat("dd.MM.yyyy").parse(ticketDetails.getStart_time());
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);

        c.add(Calendar.HOUR, 7 * 24);
        Date maxDate = c.getTime();
        if (dateStart.after(now) && dateStart.before(dateEnd) && dateEnd.before(maxDate)) {
            return venueRepository.findById(location).map(venue -> priceService.getWeatherInfluence(venue, dateStart));

        } else{
        return venueRepository.findById(location).map(venue ->
                priceService.getDemandInfluence(venue))
                ;}
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
