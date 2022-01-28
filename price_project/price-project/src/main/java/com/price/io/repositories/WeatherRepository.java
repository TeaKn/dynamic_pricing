package com.price.io.repositories;

import com.price.io.entity.WeatherEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface WeatherRepository extends ReactiveCrudRepository<WeatherEntity, String> {

    @Query("SELECT * FROM weatherEntity WHERE date = :date")
    Flux<WeatherEntity> findByDate(String date);

}