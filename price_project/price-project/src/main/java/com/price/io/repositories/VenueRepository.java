package com.price.io.repositories;


import com.price.io.entity.VenueEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VenueRepository extends ReactiveCrudRepository<VenueEntity, Long> {

    @Query("{'name': ?0}")
    Mono <VenueEntity> findByVenueName(String name);


}
