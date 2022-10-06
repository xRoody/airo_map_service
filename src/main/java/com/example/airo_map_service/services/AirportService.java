package com.example.airo_map_service.services;

import com.example.airo_map_service.domain.Airport;
import reactor.core.publisher.Flux;

public interface AirportService extends EnableDisableEntityService<Airport, Integer> {
    Flux<Airport> findAllByCityId(Integer cityId);
    Flux<Airport> findAllWithActive();
}
