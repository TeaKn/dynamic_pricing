package com.price.io.repositories;


import com.price.io.entity.VenueEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface VenueRepository extends ReactiveCrudRepository<VenueEntity, Integer> {

    @Query("SELECT * FROM venueEntity WHERE venue_id = :venue_id")
    Mono<VenueEntity> findByVenueId(Integer venue_id);

}
