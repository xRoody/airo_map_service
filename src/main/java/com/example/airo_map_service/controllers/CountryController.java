package com.example.airo_map_service.controllers;

import com.example.airo_map_service.domain.City;
import com.example.airo_map_service.domain.Country;
import com.example.airo_map_service.services.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/countries")
public class CountryController {
    private CountryService countryService;

    @GetMapping("/all")
    public ResponseEntity<Flux<Country>> findAll(){
        return ResponseEntity.ok(countryService.findAll());
    }

    @GetMapping
    public ResponseEntity<Flux<Country>> findAllActive(){
        return ResponseEntity.ok(countryService.findAllWithActive());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mono<Country>>> findById(@PathVariable Integer id){
        return countryService.findById(id)
                .defaultIfEmpty(new Country())
                .flatMap(x-> {
                    if (x.getId() == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.just(ResponseEntity.ok(Mono.just(x)));
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Integer id){
        return ResponseEntity.ok(countryService.deleteById(id));
    }

    @PostMapping
    public ResponseEntity<Mono<Country>> save(@RequestBody Country city){
        return ResponseEntity.ok(countryService.save(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flux<Object>> update(@RequestBody Country city, @PathVariable Integer id){
        Flux<Object> mono=Flux.empty();
        if (city.getActive()!=null){
            if (city.getActive()) mono=mono.concatWith(countryService.enable(id));
            else mono=mono.concatWith(countryService.disable(id));
        }
        return ResponseEntity.ok(mono.concatWith(countryService.update(city, id)));
    }
}
