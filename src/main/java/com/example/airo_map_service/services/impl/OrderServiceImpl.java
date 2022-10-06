package com.example.airo_map_service.services.impl;

import com.example.airo_map_service.domain.FlyOrder;
import com.example.airo_map_service.repositories.OrderRepo;
import com.example.airo_map_service.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepo orderRepo;

    @Override
    public Mono<FlyOrder> findById(Integer integer) {
        return orderRepo.findById(integer);
    }

    @Override
    public Flux<FlyOrder> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Mono<Void> deleteById(Integer integer) {
        return orderRepo.deleteById(integer);
    }

    @Override
    public Mono<Void> delete(FlyOrder entity) {
        return orderRepo.delete(entity);
    }

    @Override
    public Mono<FlyOrder> save(FlyOrder entity) {
        return orderRepo.save(entity);
    }

    @Override
    public Mono<FlyOrder> update(FlyOrder entity, Integer integer) {
        return orderRepo.findById(integer)
                .flatMap(x->{
                    x.setId(integer);
                    if (entity.getFromDate()!=null) x.setFromDate(entity.getFromDate());
                    if (entity.getToDate()!=null) x.setToDate(entity.getToDate());
                    if (entity.getFromId()!=null) x.setFromId(entity.getFromId());
                    if (entity.getToId()!=null) x.setToId(entity.getToId());
                    if (entity.getPlaneNum()!=null) x.setPlaneNum(entity.getPlaneNum());
                    if (entity.getPrice()!=null) x.setPrice(entity.getPrice());
                    if (entity.getCompanyId()!=null) x.setCompanyId(entity.getCompanyId());
                    return orderRepo.save(x);
                });
    }

    @Override
    public Flux<FlyOrder> findAllBetweenCities(Integer from, Integer to) {
        return orderRepo.findAllBetweenCities(from, to);
    }

    public Flux<FlyOrder> findAllBetweenAirports(Integer from, Integer to){
        return orderRepo.findAllBetweenAirports(from, to);
    }

}
