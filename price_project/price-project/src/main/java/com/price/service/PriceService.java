package com.price.service;

import com.price.io.entity.VenueEntity;
import com.price.io.entity.WeatherEntity;
import com.price.io.repositories.WeatherRepository;
import com.price.service.client.BQClient;
import com.price.service.client.WeatherClient;
import com.price.shared.dto.ForecastDemand;
import com.price.shared.dto.TicketPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.LocalDate;
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

        //Localdate.now() + 7 days, but for now hardcoded to this crap beacuse weather api not functional
        if(localDate.isBefore(LocalDate.of(2022, 2, 21))) {

        Flux<WeatherEntity> byDate = weatherRepository.findByDate(localDate);
        return byDate.map(weatherEntity -> {
            double factor;
            Integer max_temp = weatherEntity.getTX_C();
            Integer min_temp = weatherEntity.getTN_C();
            int avg_temp = (max_temp + min_temp) / 2;
            Integer wind_f = weatherEntity.getFF_KMH();
            factor = 13.12 + (0.6215 * avg_temp) - (11.37 * wind_f * 0.16) + (0.3965 * avg_temp * wind_f * 0.16); // ta formula naj bi veljala za temp pod -10 celzija
            ticketPrice.setWindChill(factor);
            return ticketPrice;
        });
        }
        else {
            ticketPrice.setWindChill(9999.99);
            return Flux.just(ticketPrice);
        }

    }

    public Flux<TicketPrice> getDemandInfluence(VenueEntity venueEntity, LocalDate dateStart, LocalDate dateEnd) {

        //bq, bq effect is always present
        List<ForecastDemand> forecast = null;
        try {
            forecast = bqClient.explainForecast();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double dailyAverage = 27000.0; // was hardcoded for based on arima values for week 14.02 - 20.02

        assert forecast != null;
        return Flux.fromIterable(forecast)
                .filter(forecastDemand -> forecastDemand.getDate().isAfter(dateStart.minusDays(1)) && forecastDemand.getDate().isBefore(dateEnd.plusDays(1))) // vržem ven nepotrebne podatke
                .map(demand -> {
                    double d = (demand.getDemand() - dailyAverage) / dailyAverage;
                    Double price = venueEntity.getAdult_base_price() + d * 10;

                    TicketPrice ticketPrice = new TicketPrice();
                    ticketPrice.setDemandPrice(price);
                    ticketPrice.setDate(demand.getDate()); // ta datum od demand je star, prilagojen da dela za leto 2022
                    ticketPrice.setBasePrice(venueEntity.getAdult_base_price());
                    ticketPrice.setPriceMax(venueEntity.getPrice_max());
                    ticketPrice.setPriceMin(venueEntity.getPrice_min());
                    ticketPrice.setDemandNumber(Math.round(demand.getDemand()));
                    return ticketPrice;
                });
    }

    public Flux<TicketPrice> getPrices(TicketPrice ticketPrice){
        double wind_chill_optimal = -9.5;
        double[] change_vector = {10, -2, -5, -10}; // in percentage
        double wind_chill_index = ticketPrice.getWindChill();
        double weather_influence;

        //apply demand

        //apply weather influence
        if (Math.abs(wind_chill_index - 9999.99) < 0.000001d) {
            weather_influence = 0.0;
            ticketPrice.setWeatherInfluence(0.0);
        }
        else if (10 <= wind_chill_index) {
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
        else if (wind_chill_optimal <= wind_chill_index) {
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
        else if (-30 <= wind_chill_index){
            weather_influence = change_vector[3];
            ticketPrice.setWeatherInfluence(change_vector[3]);
        }
        else {weather_influence = 0.0;
        ticketPrice.setWeatherInfluence(0.0);} // case out of bounds

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
