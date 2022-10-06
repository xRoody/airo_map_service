package com.example.airo_map_service.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntityService<T,ID> {
    Mono<T> findById(ID id);
    Flux<T> findAll();
    Mono<Void> deleteById(ID id);
    Mono<Void> delete(T entity);
    Mono<T> save(T entity);
    Mono<T> update(T entity, ID id);
}
