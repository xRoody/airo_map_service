package com.example.airo_map_service.services.impl;

import com.example.airo_map_service.domain.Country;
import com.example.airo_map_service.repositories.CountryRepo;
import com.example.airo_map_service.services.CountryService;
import com.example.airo_map_service.services.impl.graph.GraphStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private CountryRepo countryRepo;
    private GraphStorage graphStorage;

    @Override
    public Mono<Void> disable(Integer integer) {
        return graphStorage.disableCountry(integer);
    }

    @Override
    public Mono<Void> enable(Integer integer) {
        return graphStorage.enableCountry(integer);
    }

    @Override
    public Mono<Country> findById(Integer integer) {
        return countryRepo.findById(integer);
    }

    @Override
    public Flux<Country> findAll() {
        return countryRepo.findAll();
    }

    @Override
    public Mono<Void> deleteById(Integer integer) {
        return countryRepo.deleteById(integer);
    }

    @Override
    public Mono<Void> delete(Country entity) {
        return countryRepo.delete(entity);
    }

    @Override
    public Mono<Country> save(Country entity) {
        return countryRepo.save(entity);
    }

    @Override
    public Mono<Country> update(Country entity, Integer integer) {
        return countryRepo.findById(integer)
                .flatMap(x -> {
                    x.setId(integer);
                    if (entity.getDescription() != null) x.setDescription(entity.getDescription());
                    if (entity.getPreview() != null) x.setPreview(entity.getPreview());
                    if (entity.getTitle() != null) x.setTitle(entity.getTitle());
                    return countryRepo.save(x);
                });
    }

    @Override
    public Flux<Country> findAllWithActive() {
        return countryRepo.findAllByActiveTrue();
    }
}
