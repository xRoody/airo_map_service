package com.example.airo_map_service.services;

import com.example.airo_map_service.domain.FlyOrder;
import reactor.core.publisher.Flux;

public interface OrderService extends EntityService<FlyOrder, Integer>{

    Flux<FlyOrder> findAllBetweenCities(Integer from, Integer to);
    Flux<FlyOrder> findAllBetweenAirports(Integer from, Integer to);
}
