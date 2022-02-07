package com.price.service;

import com.price.io.entity.VenueEntity;
import com.price.io.entity.WeatherEntity;
import com.price.io.repositories.WeatherRepository;
import com.price.service.client.BQClient;
import com.price.service.client.WeatherClient;
import com.price.shared.dto.ForecastDemand;
import com.price.shared.dto.TicketPrice;
import com.price.ui.model.request.TicketRequestModel;
import com.price.ui.model.response.Day;
import com.price.ui.model.response.Forecast;
import com.price.ui.model.response.ForecastFlux;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
public class PriceService {

    @Autowired
    BQClient bqClient;

    @Autowired
    WeatherClient weatherClient;
    private WeatherRepository weatherRepository;

    public Mono<List<Double>> getWeatherInfluence(VenueEntity venueEntity, Date date) {

        String location = venueEntity.getName();
        List<TicketPrice> ticketPriceList = new ArrayList<>();

        Flux<WeatherEntity> byDate = weatherRepository.findByDate(date);
        return byDate.map(weatherEntity -> {
                int max_temp = weatherEntity.getTX_C();
                int min_temp = weatherEntity.getTN_C();
                int avg_temp = (max_temp + min_temp) / 2;
                int wind_f = weatherEntity.getFF_KMH();
                int wind_x = weatherEntity.getFX_KMH();
                int avg_wind = (wind_f + wind_x) / 2; //sounds like bullshit
                double factor = 13.12 + (0.6215 * avg_temp) - (11.37 * avg_wind * 0.16) + (0.3965 * avg_temp * avg_wind * 0.16); // ta formula naj bi veljala za temp pod -10 celzija
                return factor;
        }).collectList();


    }

    public Stream<Object> getDemandInfluence(VenueEntity venueEntity) {

        //bq, bq effect is always present
        List<ForecastDemand> forecast = null;
        try {
            forecast = bqClient.explainForecast();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double dailyAverage = 45000.0; // was hardcoded for Airolo

        assert forecast != null;
        return forecast.stream()
                .map(demand -> {
                    double d = (demand.getDemand() - dailyAverage) / dailyAverage;
                    Double price = venueEntity.getAdult_base_price() + d * 10;

                    TicketPrice ticketPrice = new TicketPrice();
                    ticketPrice.setDemandPrice(price);
                    ticketPrice.setDate(demand.getDate());
                    return ticketPrice;
                });


    }
    public void getPrices(TicketRequestModel ticketDetails) throws ParseException {
        Double windChillOptimal = -9.5;

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
        //pipeline
        //apply demand
        //apply weather influence

        //final price bounds
        //if (price > venueEntity.getPrice_max()) {
        //    price = venueEntity.getPrice_max();
        //}
        //if (price < venueEntity.getPrice_min()) {
        //    price = venueEntity.getPrice_min();
        //}


    }

}
