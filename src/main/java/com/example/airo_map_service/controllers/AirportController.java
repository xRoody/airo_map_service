package com.example.airo_map_service.controllers;

import com.example.airo_map_service.domain.Airport;
import com.example.airo_map_service.services.AirportService;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/airports")
public class AirportController {
    private AirportService airportService;

    @GetMapping("/all")
    public ResponseEntity<Flux<Airport>> findAll(){
        return ResponseEntity.ok(airportService.findAll());
    }

    @GetMapping
    public ResponseEntity<Flux<Airport>> findAllActive(){
        return ResponseEntity.ok(airportService.findAllWithActive());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mono<Airport>>> findById(@PathVariable Integer id){
        return airportService.findById(id)
                .defaultIfEmpty(new Airport(null, null,null,null,null))
                .flatMap(x-> {
                    if (x.getId() == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.just(ResponseEntity.ok(Mono.just(x)));
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Integer id){
        return ResponseEntity.ok(airportService.deleteById(id));
    }

    @PostMapping
    public ResponseEntity<Mono<Airport>> save(@RequestBody Airport airport){
        return ResponseEntity.ok(airportService.save(airport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flux<Object>> update(@RequestBody Airport airport, @PathVariable Integer id){
        Flux<Object> mono=Flux.empty();
        if (airport.getActive()!=null){
            if (airport.getActive()) mono=mono.concatWith(airportService.enable(id));
            else mono=mono.concatWith(airportService.disable(id));
        }
        return ResponseEntity.ok(mono.concatWith(airportService.update(airport, id)));
    }


}
