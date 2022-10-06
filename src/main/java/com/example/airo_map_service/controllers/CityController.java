package com.example.airo_map_service.controllers;

import com.example.airo_map_service.domain.Airport;
import com.example.airo_map_service.domain.City;
import com.example.airo_map_service.services.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private CityService cityService;

    @GetMapping("/all")
    public ResponseEntity<Flux<City>> findAll(){
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping
    public ResponseEntity<Flux<City>> findAllActive(){
        return ResponseEntity.ok(cityService.findAllWithActive());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mono<City>>> findById(@PathVariable Integer id){
        return cityService.findById(id)
                .defaultIfEmpty(new City())
                .flatMap(x-> {
                    if (x.getId() == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.just(ResponseEntity.ok(Mono.just(x)));
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Integer id){
        return ResponseEntity.ok(cityService.deleteById(id));
    }

    @PostMapping
    public ResponseEntity<Mono<City>> save(@RequestBody City city){
        return ResponseEntity.ok(cityService.save(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flux<Object>> update(@RequestBody City city, @PathVariable Integer id){
        Flux<Object> mono=Flux.empty();
        if (city.getActive()!=null){
            if (city.getActive()) mono=mono.concatWith(cityService.enable(id));
            else mono=mono.concatWith(cityService.disable(id));
        }
        return ResponseEntity.ok(mono.concatWith(cityService.update(city, id)));
    }

}
