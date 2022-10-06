package com.example.airo_map_service.controllers;

import com.example.airo_map_service.domain.Airport;
import com.example.airo_map_service.domain.FlyOrder;
import com.example.airo_map_service.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class FlyOrderMappingController {
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<Flux<FlyOrder>> findAll(){
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mono<FlyOrder>>> findById(@PathVariable Integer id){
        return orderService.findById(id)
                .defaultIfEmpty(new FlyOrder())
                .flatMap(x-> {
                    if (x.getId() == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.just(ResponseEntity.ok(Mono.just(x)));
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.deleteById(id));
    }

    @PostMapping
    public ResponseEntity<Mono<FlyOrder>> save(@RequestBody FlyOrder order){
        return ResponseEntity.ok(orderService.save(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flux<Object>> update(@RequestBody FlyOrder airport, @PathVariable Integer id){
        Flux<Object> mono=Flux.empty();
        return ResponseEntity.ok(mono.concatWith(orderService.update(airport, id)));
    }

}
