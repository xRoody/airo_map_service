package com.example.airo_map_service.services;

import com.example.airo_map_service.domain.Airport;
import com.example.airo_map_service.domain.Country;
import reactor.core.publisher.Flux;

public interface CountryService extends EnableDisableEntityService<Country, Integer>{
    Flux<Country> findAllWithActive();
}
