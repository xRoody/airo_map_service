package com.example.airo_map_service.services;

import com.example.airo_map_service.services.impl.graph.RoadNode;
import reactor.core.publisher.Mono;

public interface PathService {
    Mono<RoadNode> findTheCheapestWayBetweenCity(Integer fromId, Integer toId);
    Mono<RoadNode> findTheCheapestWayBetweenAirports(Integer fromId, Integer toId);
    public Mono<RoadNode> findTheFastestWayBetweenAirports(Integer fromId, Integer toId);
    public Mono<RoadNode> findTheFastestWayBetweenCity(Integer fromId, Integer toId);
}
