package com.price.io.repositories;

import com.price.io.entity.WeatherEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Date;

public interface WeatherRepository extends ReactiveCrudRepository<WeatherEntity, Long> {

    @Query("SELECT * FROM weather WHERE local_date_time = :date")
    Flux<WeatherEntity> findByDate(@Param("date") LocalDate date);

    @Query("DELETE FROM weather")
    Mono<Void> deleteAll();
}
