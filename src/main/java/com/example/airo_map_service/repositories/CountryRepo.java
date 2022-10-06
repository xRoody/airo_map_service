package com.example.airo_map_service.repositories;

import com.example.airo_map_service.domain.Airport;
import com.example.airo_map_service.domain.Country;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CountryRepo extends R2dbcRepository<Country, Integer> {
    Flux<Country> findAllByActiveTrue();
}
