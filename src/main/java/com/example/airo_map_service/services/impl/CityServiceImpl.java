package com.example.airo_map_service.services.impl;

import com.example.airo_map_service.domain.City;
import com.example.airo_map_service.repositories.CityRepo;
import com.example.airo_map_service.services.CityService;
import com.example.airo_map_service.services.impl.graph.GraphStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private CityRepo cityRepo;
    private GraphStorage graphStorage;

    @Override
    public Mono<Void> disable(Integer integer) {
        return graphStorage.disableCity(integer);
    }

    @Override
    public Mono<Void> enable(Integer integer) {
        return graphStorage.enableCity(integer);
    }

    @Override
    public Mono<City> findById(Integer integer) {
        return cityRepo.findById(integer);
    }

    @Override
    public Flux<City> findAll() {
        return cityRepo.findAll();
    }

    @Override
    public Mono<Void> deleteById(Integer integer) {
        return cityRepo.deleteById(integer);
    }

    @Override
    public Mono<Void> delete(City entity) {
        return cityRepo.delete(entity);
    }

    @Override
    public Mono<City> save(City entity) {
        return cityRepo.save(entity);
    }

    @Override
    public Mono<City> update(City entity, Integer integer) {
        return cityRepo.findById(integer)
                .flatMap(x -> {
                    x.setId(integer);
                    if (entity.getPreview() != null) x.setPreview(entity.getPreview());
                    if (entity.getDescription() != null) x.setDescription(entity.getDescription());
                    if (entity.getCountryId()!=null) x.setCountryId(entity.getCountryId());
                    if (entity.getTitle() != null) x.setTitle(entity.getTitle());
                    return cityRepo.save(x);
                });
    }

    @Override
    public Flux<City> findAllWithActive() {
        return cityRepo.findAllWithActive();
    }
}
