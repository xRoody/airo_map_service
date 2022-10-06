package com.example.airo_map_service.controllers;

import com.example.airo_map_service.domain.Airport;
import com.example.airo_map_service.domain.Company;
import com.example.airo_map_service.services.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    @GetMapping("/all")
    public ResponseEntity<Flux<Company>> findAll(){
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mono<Company>>> findById(@PathVariable Integer id){
        return companyService.findById(id)
                .defaultIfEmpty(new Company())
                .flatMap(x-> {
                    if (x.getId() == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.just(ResponseEntity.ok(Mono.just(x)));
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Integer id){
        return ResponseEntity.ok(companyService.deleteById(id));
    }

    @PostMapping
    public ResponseEntity<Mono<Company>> save(@RequestBody Company airport){
        return ResponseEntity.ok(companyService.save(airport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flux<Object>> update(@RequestBody Company airport, @PathVariable Integer id){
        Flux<Object> mono=Flux.empty();
        return ResponseEntity.ok(mono.concatWith(companyService.update(airport, id)));
    }
}
