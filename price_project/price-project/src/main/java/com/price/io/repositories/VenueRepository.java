package com.price.io.repositories;


import com.price.io.entity.VenueEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Repository
public interface VenueRepository extends ReactiveCrudRepository<VenueEntity, String> {

    @NonNull
    @Query("SELECT * FROM venues WHERE name = :name")
    Mono <VenueEntity> findById(@NonNull @Param("name") String name);


}
