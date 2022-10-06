package com.example.airo_map_service.services.impl;

import com.example.airo_map_service.domain.City;
import com.example.airo_map_service.domain.Company;
import com.example.airo_map_service.repositories.CompanyRepo;
import com.example.airo_map_service.services.CityService;
import com.example.airo_map_service.services.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepo companyRepo;


    @Override
    public Mono<Company> findById(Integer integer) {
        return companyRepo.findById(integer);
    }

    @Override
    public Flux<Company> findAll() {
        return companyRepo.findAll();
    }

    @Override
    public Mono<Void> deleteById(Integer integer) {
        return companyRepo.deleteById(integer);
    }

    @Override
    public Mono<Void> delete(Company entity) {
        return companyRepo.delete(entity);
    }

    @Override
    public Mono<Company> save(Company entity) {
        return companyRepo.save(entity);
    }

    @Override
    public Mono<Company> update(Company entity, Integer integer) {
        return companyRepo.findById(integer)
                .flatMap(x -> {
                    x.setId(integer);
                    if (entity.getDescription() != null) x.setDescription(entity.getDescription());
                    if (entity.getTitle() != null) x.setTitle(entity.getTitle());
                    return companyRepo.save(x);
                });
    }
}
