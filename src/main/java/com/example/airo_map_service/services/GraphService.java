package com.example.airo_map_service.services;

import com.example.airo_map_service.services.impl.graph.RoadNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GraphService {
    Flux<RoadNode> findAllNodesFrom(Integer id);
    boolean isActiveCity(Integer id);
    boolean isActiveAirport(Integer id);
    Mono<Void> disableCountry(Integer id);
    Mono<Void> enableCountry(Integer id);
    Mono<Void> enableCity(Integer id);
    Mono<Void> disableCity(Integer id);
    Mono<Void> disableAirport(Integer id);
    Mono<Void> enableAirport(Integer id);
    Flux<Integer> getAirportsIdsByCityId(Integer cityId);
}
