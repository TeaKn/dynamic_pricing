package com.price.service;

import com.price.io.entity.VenueEntity;
import com.price.io.entity.WeatherEntity;
import com.price.io.repositories.WeatherRepository;
import com.price.service.client.BQClient;
import com.price.service.client.WeatherClient;
import com.price.shared.dto.ForecastDemand;
import com.price.shared.dto.TicketPrice;
import com.price.ui.model.request.TicketRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PriceService {

    @Autowired
    BQClient bqClient;

    @Autowired
    WeatherClient weatherClient;

    @Autowired
    WeatherRepository weatherRepository;

    public Flux<TicketPrice> getWeatherInfluence(TicketPrice ticketPrice) {

        LocalDate localDate = ticketPrice.getDate();
        //Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Flux<WeatherEntity> byDate = weatherRepository.findByDate(localDate);
        return byDate.map(weatherEntity -> {
            Double factor;
            Integer max_temp = weatherEntity.getTX_C();
            Integer min_temp = weatherEntity.getTN_C();
            int avg_temp = (max_temp + min_temp) / 2;
            Integer wind_f = weatherEntity.getFF_KMH();
            Integer wind_x = weatherEntity.getFX_KMH();
            int avg_wind = (wind_f + wind_x) / 2; //sounds like bullshit
            factor = 13.12 + (0.6215 * avg_temp) - (11.37 * avg_wind * 0.16) + (0.3965 * avg_temp * avg_wind * 0.16); // ta formula naj bi veljala za temp pod -10 celzija
            System.out.println("I am HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            System.out.println(factor);
            ticketPrice.setWeatherPrice(factor);
            return ticketPrice;
        });


    }

    public Flux<TicketPrice> getDemandInfluence(VenueEntity venueEntity, LocalDate dateStart, LocalDate dateEnd) {

        //bq, bq effect is always present
        List<ForecastDemand> forecast = null;
        try {
            forecast = bqClient.explainForecast();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double dailyAverage = 45000.0; // was hardcoded for Airolo

        assert forecast != null;
        return Flux.fromIterable(forecast)
                .filter(forecastDemand -> forecastDemand.getDate().isAfter(dateStart) && forecastDemand.getDate().isBefore(dateEnd)) // vržem ven nepotrebne podatke
                .map(demand -> {
                    double d = (demand.getDemand() - dailyAverage) / dailyAverage;
                    Double price = venueEntity.getAdult_base_price() + d * 10;

                    TicketPrice ticketPrice = new TicketPrice();
                    ticketPrice.setDemandPrice(price);
                    ticketPrice.setDate(demand.getDate()); // ta datum od demand je krneki
                    return ticketPrice;
                });


    }
    public void getPrices(TicketRequestModel ticketDetails) throws ParseException {
        Double windChillOptimal = -9.5;

        // parse input
        String location = ticketDetails.getVenue();

        // if datum in tti
        //Date dateEnd = new SimpleDateFormat("dd.MM.yyyy").parse(ticketDetails.getEnd_time());
        //Date dateStart = new SimpleDateFormat("dd.MM.yyyy").parse(ticketDetails.getStart_time());
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

    // TODO: Function<Double, Double > priceBounding() idk anymore
    // TODO: Fucntion<List, List> DemandInfluence() idk anymore




}
