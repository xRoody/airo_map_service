package com.example.airo_map_service.services.impl.graph;


import com.example.airo_map_service.repositories.AirportRepo;
import com.example.airo_map_service.repositories.CityRepo;
import com.example.airo_map_service.repositories.RoadNodeRepo;
import com.example.airo_map_service.services.GraphService;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class GraphStorage implements GraphService {
    private final AsyncLoadingCache<Integer, Set<Integer>> cityIdAirports;
    private final AsyncLoadingCache<Integer, Set<RoadNode>> airportIdRoad;
    private final AsyncLoadingCache<Integer, Set<Integer>> countryIdAirports;

    @Autowired
    public GraphStorage(RoadNodeRepo roadNodeRepo, AirportRepo airportService, CityRepo cityRepo) {
        airportIdRoad = Caffeine.newBuilder()
                .maximumSize(100000)
                .expireAfterAccess(15L, TimeUnit.MINUTES)
                .buildAsync((id, executor) ->
                        roadNodeRepo.findAllNodesFrom(id).collect(() -> new HashSet<RoadNode>(), (HashSet::add)).toFuture()
                );
        cityIdAirports = Caffeine.newBuilder()
                .buildAsync((id, executor) ->
                        airportService.findAllByCityId(id).collect(() -> new HashSet<Integer>(), ((integers, airport) -> integers.add(airport.getId()))).toFuture()
                );
        countryIdAirports = Caffeine.newBuilder()
                .buildAsync((id, executor) ->
                        cityRepo.findAllByCountryIdAndActive(id).collect(() -> new HashSet<Integer>(), ((integers, airport) -> integers.add(airport.getId()))).toFuture()
                );
        airportService.findAllWithActive().collect(() -> new HashMap<Integer, Set<Integer>>(), ((map, airport) -> {
                    if (!map.containsKey(airport.getCityId())) map.put(airport.getCityId(), new HashSet<>());
                    map.get(airport.getCityId()).add(airport.getId());
                }))
                .flux()
                .flatMap(x -> Flux.fromIterable(x.entrySet()))
                .subscribe(x->cityIdAirports.synchronous().put(x.getKey(), x.getValue()));
    }

    public boolean isActiveCity(Integer id) {
        return cityIdAirports.asMap().containsKey(id);
    }

    public boolean isActiveAirport(Integer id) {
        return airportIdRoad.asMap().containsKey(id);
    }

    public Flux<RoadNode> findAllNodesFrom(Integer id) {
        return Mono.fromCompletionStage(airportIdRoad.get(id)).flux().flatMap(Flux::fromIterable);
    }

    public Mono<Void> disableCountry(Integer id){
        return Mono.fromCompletionStage(countryIdAirports.get(id))
                .flux()
                .flatMap(Flux::fromIterable)
                .flatMap(this::disableCity)
                .ignoreElements();
    }

    public Mono<Void> enableCountry(Integer id){
       return Mono.fromCompletionStage(countryIdAirports.get(id))
                .flux()
                .flatMap(Flux::fromIterable)
                .flatMap(this::enableCity)
                .ignoreElements();
    }

    public Mono<Void> enableCity(Integer id) {
        return Mono.just(id)
                .flatMap(x->Mono.fromCompletionStage(cityIdAirports.get(x)))
                .flux()
                .flatMap(Flux::fromIterable)
                .flatMap(this::enableAirport)
                .ignoreElements();
    }

    public Mono<Void> disableCity(Integer id) {
        return Mono.fromCompletionStage(cityIdAirports.asMap().remove(id))
                .flux()
                .flatMap(Flux::fromIterable)
                .flatMap(this::disableAirport)
                .ignoreElements();
    }

    public Mono<Void> disableAirport(Integer id) {
        return Mono.fromCompletionStage(airportIdRoad.asMap().remove(id)).then();
    }

    public Mono<Void> enableAirport(Integer id) {
        return Mono.fromCompletionStage(airportIdRoad.get(id)).then();
    }



   public Flux<Integer> getAirportsIdsByCityId(Integer cityId) {
        return Mono.fromCompletionStage(cityIdAirports.get(cityId)).flux().flatMap(Flux::fromIterable);
    }
}
