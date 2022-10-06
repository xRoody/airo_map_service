package com.example.airo_map_service.services;

import reactor.core.publisher.Mono;

public interface EnableDisableEntityService<T,ID> extends EntityService<T,ID>{
    Mono<Void> disable(ID id);
    Mono<Void> enable(ID id);
}
