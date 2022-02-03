package com.price.io.repositories;


import com.price.io.entity.VenueEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VenueRepository extends ReactiveCrudRepository<VenueEntity, Long> {

    @Query("SELECT * FROM venues WHERE name = :name")
    Mono <VenueEntity> findByVenueName(@Param("name") String name);


}
