package com.price.service;

import com.google.cloud.bigquery.BigQuery;
import com.price.io.entity.VenueEntity;
import com.price.service.client.BQClient;
import com.price.shared.dto.ForecastDemand;
import com.price.shared.dto.TicketPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    BQClient bqClient;

    public Flux<TicketPrice> getPrices(VenueEntity venueEntity) {

        //bq
        List<ForecastDemand> forecast = null;
        try {
            forecast = bqClient.explainForecast();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double dailyAverage = 45000.0;

        return Flux.fromStream(forecast.stream())
                .map(demand -> {
                    double d = (demand.getDemand() - dailyAverage) / dailyAverage;
                    Double price = venueEntity.getAdult_base_price() + d * 10;
                    if (price > venueEntity.getPrice_max()) {
                        price = venueEntity.getPrice_max();
                    }
                    if (price < venueEntity.getPrice_min()) {
                        price = venueEntity.getPrice_min();
                    }
                    TicketPrice ticketPrice = new TicketPrice();
                    ticketPrice.setPrice(price);
                    ticketPrice.setDate(demand.getDate());
                    return ticketPrice;
                });


    }

}
