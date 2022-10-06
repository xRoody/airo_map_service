package com.example.airo_map_service.services.impl;

import com.example.airo_map_service.domain.Airport;
import com.example.airo_map_service.repositories.AirportRepo;
import com.example.airo_map_service.services.AirportService;
import com.example.airo_map_service.services.impl.graph.GraphStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AirportServiceImpl implements AirportService {
    private final AirportRepo airportRepo;

    public AirportServiceImpl(AirportRepo airportRepo) {
        this.airportRepo = airportRepo;
    }

    private GraphStorage graphStorage;

    @Autowired
    public void setGraphStorage(GraphStorage graphStorage) {
        this.graphStorage = graphStorage;
    }

    @Override
    public Mono<Void> disable(Integer integer) {
        return graphStorage.disableAirport(integer);
    }

    @Override
    public Mono<Void> enable(Integer integer) {
        return graphStorage.enableAirport(integer);
    }

    @Override
    public Flux<Airport> findAllWithActive() {
        return airportRepo.findAllWithActive();
    }

    @Override
    public Flux<Airport> findAllByCityId(Integer cityId) {
        return airportRepo.findAllByCityId(cityId);
    }

    @Override
    public Mono<Airport> findById(Integer integer) {
        return airportRepo.findById(integer);
    }

    @Override
    public Flux<Airport> findAll() {
        return airportRepo.findAll();
    }

    @Override
    public Mono<Void> deleteById(Integer integer) {
        return airportRepo.deleteById(integer);
    }

    @Override
    public Mono<Void> delete(Airport entity) {
        return airportRepo.delete(entity);
    }

    @Override
    public Mono<Airport> save(Airport entity) {
        return airportRepo.save(entity);
    }

    @Override
    public Mono<Airport> update(Airport entity, Integer integer) {
        return airportRepo.findById(integer)
                .flatMap(x -> {
                    x.setId(integer);
                    if (entity.getCityId() != null) x.setCityId(entity.getCityId());
                    if (entity.getPreview() != null) x.setPreview(entity.getPreview());
                    if (entity.getTitle() != null) x.setTitle(entity.getTitle());
                    return airportRepo.save(x);
                });
    }
}
