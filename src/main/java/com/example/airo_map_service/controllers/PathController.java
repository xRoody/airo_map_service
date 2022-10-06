package com.example.airo_map_service.controllers;

import com.example.airo_map_service.domain.FlyOrder;
import com.example.airo_map_service.dtos.FlyOrderDTO;
import com.example.airo_map_service.facades.PathFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/*TODO:
*  Add fastest paths between points (cities and airports)
*  and prepare dto contains all straight, fastest and cheapest ways.
* */

@RestController
@AllArgsConstructor
@RequestMapping("/pathfinder")
public class PathController {
    private PathFacade pathFacade;

    @GetMapping("/cheapest/cities")
    public ResponseEntity<Mono<List<FlyOrderDTO>>> getCheapestPathBetweenCities(@RequestParam(name = "from") Integer from, @RequestParam(name = "to") Integer to){
        return ResponseEntity.ok(pathFacade.findTheCheapestWayBetweenCities(from, to));
    }

    @GetMapping("/cheapest/airports")
    public ResponseEntity<Mono<List<FlyOrderDTO>>> getCheapestPathBetweenAirports(@RequestParam(name = "from") Integer from, @RequestParam(name = "to") Integer to){
        return ResponseEntity.ok(pathFacade.findTheCheapestWayBetweenAirports(from,to));
    }

    @GetMapping("/fastest/airports")
    public ResponseEntity<Mono<List<FlyOrderDTO>>> getFastestPathBetweenAirports(@RequestParam(name = "from") Integer from, @RequestParam(name = "to") Integer to){
        return ResponseEntity.ok(pathFacade.findTheFastestWayBetweenAirports(from, to));
    }

    @GetMapping("/fastest/cities")
    public ResponseEntity<Mono<List<FlyOrderDTO>>> getFastestPathBetweenCity(@RequestParam(name = "from") Integer from, @RequestParam(name = "to") Integer to){
        return ResponseEntity.ok(pathFacade.findTheFastestWayBetweenCities(from, to));
    }

    @GetMapping("/all/airports")
    public ResponseEntity<Mono<List<List<FlyOrderDTO>>>> getAllPathsBetweenAirports(@RequestParam(name = "from") Integer from, @RequestParam(name = "to") Integer to){
        return ResponseEntity.ok(pathFacade.findAllBestWaysBetweenAirports(from, to));
    }

    @GetMapping("/all/cities")
    public ResponseEntity<Mono<List<List<FlyOrderDTO>>>> getAllPathsBetweenCity(@RequestParam(name = "from") Integer from, @RequestParam(name = "to") Integer to){
        return ResponseEntity.ok(pathFacade.findAllBestWaysBetweenCities(from, to));
    }

}
