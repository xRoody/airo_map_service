package com.example.airo_map_service.services;

import com.example.airo_map_service.domain.Airport;
import com.example.airo_map_service.domain.City;
import reactor.core.publisher.Flux;

public interface CityService extends EnableDisableEntityService<City, Integer>{


    Flux<City> findAllWithActive();
}
