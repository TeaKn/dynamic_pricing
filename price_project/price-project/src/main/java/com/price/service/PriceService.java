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

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
            Integer wind_x = weatherEntity.getFX_KMH(); // gust peak
            factor = 13.12 + (0.6215 * avg_temp) - (11.37 * wind_f * 0.16) + (0.3965 * avg_temp * wind_f * 0.16); // ta formula naj bi veljala za temp pod -10 celzija
            System.out.println("I am HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            System.out.println(factor);
            ticketPrice.setWindChill(factor);
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
                    ticketPrice.setDate(demand.getDate()); // ta datum od demand je star, prilagojen da dela za leto 2022
                    ticketPrice.setBasePrice(venueEntity.getAdult_base_price());
                    ticketPrice.setPriceMax(venueEntity.getPrice_max());
                    ticketPrice.setPriceMin(venueEntity.getPrice_min());
                    return ticketPrice;
                });


    }
    public Flux<TicketPrice> getPrices(TicketPrice ticketPrice){
        Double wind_chill_optimal = -9.5;
        double change_vector[] = {10, -2, -5, -10}; // in percentage
        double wind_chill_index = ticketPrice.getWindChill();
        double weather_influence;

        //apply demand

        //apply weather influence
        if (10 <= wind_chill_index) {
            weather_influence = change_vector[3];
            ticketPrice.setWeatherInfluence(change_vector[3]);
        }
        else if (5 <= wind_chill_index) {
            weather_influence = change_vector[2];
            ticketPrice.setWeatherInfluence(change_vector[2]);
        }
        else if (-5 <= wind_chill_index) {
            weather_influence = change_vector[1];
            ticketPrice.setWeatherInfluence(change_vector[1]);
        }
        else if (-9.5 <= wind_chill_index) {
            weather_influence = change_vector[0];
            ticketPrice.setWeatherInfluence(change_vector[0]);
        }
        else if (-15 <= wind_chill_index) {
            weather_influence = change_vector[1];
            ticketPrice.setWeatherInfluence(change_vector[1]);
        }
        else if (-25 <= wind_chill_index) {
            weather_influence = change_vector[2];
            ticketPrice.setWeatherInfluence(change_vector[2]);
        }
        else {
            weather_influence = change_vector[3];
            ticketPrice.setWeatherInfluence(change_vector[3]);
        }

        ticketPrice.setWeatherPrice(ticketPrice.getDemandPrice() + weather_influence * 1/100);

        // final price bounds
        double price = ticketPrice.getWeatherPrice();
        if (price > ticketPrice.getPriceMax()) {
            price = ticketPrice.getPriceMax();
        }
        if (price < ticketPrice.getPriceMin()) {
            price = ticketPrice.getPriceMin();
        }

        ticketPrice.setPrice(price);
        return Flux.just(ticketPrice);

    }




}
