package com.example.airo_map_service.facades;

import com.example.airo_map_service.domain.FlyOrder;
import com.example.airo_map_service.dtos.FlyOrderDTO;
import com.example.airo_map_service.repositories.FlyOrderDTORepo;
import com.example.airo_map_service.services.OrderService;
import com.example.airo_map_service.services.PathService;
import com.example.airo_map_service.services.impl.graph.RoadNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Component
@AllArgsConstructor
public class PathFacade {
    private PathService pathService;
    private FlyOrderDTORepo orderDTOService;
    private OrderService orderService;

    public Mono<List<FlyOrderDTO>> findTheCheapestWayBetweenCities(Integer fromId, Integer toId){
        return pathService
                .findTheCheapestWayBetweenCity(fromId,toId)
                .defaultIfEmpty(new RoadNode())
                .flux()
                .flatMap(x->{
                    Flux<FlyOrderDTO> orders=Flux.empty();
                    while (x!=null){
                        if (x.getOrderId()!=null) orders=orders.concatWith(orderDTOService.findById(x.getOrderId()));
                        x=x.getPrev();
                    }
                    return orders;
                })
                .collect(ArrayList::new, (List::add));
    }

    public Mono<List<FlyOrderDTO>> findTheCheapestWayBetweenAirports(Integer fromId, Integer toId){
        return pathService
                .findTheCheapestWayBetweenAirports(fromId,toId)
                .defaultIfEmpty(new RoadNode())
                .flux()
                .flatMap(x->{
                    Flux<FlyOrderDTO> orders=Flux.empty();
                    while (x!=null){
                        if (x.getOrderId()!=null) orders=orders.concatWith(orderDTOService.findById(x.getOrderId()));
                        x=x.getPrev();
                    }
                    return orders;
                })
                .collect(ArrayList::new, (List::add));
    }

    public Mono<List<FlyOrderDTO>> findTheFastestWayBetweenAirports(Integer fromId, Integer toId){
        return pathService
                .findTheFastestWayBetweenAirports(fromId,toId)
                .defaultIfEmpty(new RoadNode())
                .flux()
                .flatMap(x->{
                    Flux<FlyOrderDTO> orders=Flux.empty();
                    while (x!=null){
                        if (x.getOrderId()!=null) orders=orders.concatWith(orderDTOService.findById(x.getOrderId()));
                        x=x.getPrev();
                    }
                    return orders;
                })
                .collect(ArrayList::new, (List::add));
    }

    public Mono<List<FlyOrderDTO>> findTheFastestWayBetweenCities(Integer fromId, Integer toId){
        return pathService
                .findTheFastestWayBetweenCity(fromId,toId)
                .defaultIfEmpty(new RoadNode())
                .flux()
                .flatMap(x->{
                    Flux<FlyOrderDTO> orders=Flux.empty();
                    while (x!=null){
                        if (x.getOrderId()!=null) orders=orders.concatWith(orderDTOService.findById(x.getOrderId()));
                        x=x.getPrev();
                    }
                    return orders;
                })
                .collect(ArrayList::new, (List::add));
    }

    public Mono<List<List<FlyOrderDTO>>> findAllBestWaysBetweenAirports(Integer fromId, Integer toId){
        return orderService.findAllBetweenAirports(fromId, toId)
                .defaultIfEmpty(new FlyOrder())
                .flatMap(x->{
                    if (x.getId()!=null) return orderDTOService.findById(x.getId());
                    else return Mono.just(new FlyOrderDTO());
                })
                .map(x->{
                    List<FlyOrderDTO> list=new ArrayList<>();
                    if (x.getId()!=null) list.add(x);
                    return list;
                })
                .concatWith(findTheCheapestWayBetweenAirports(fromId, toId))
                .concatWith(findTheFastestWayBetweenAirports(fromId, toId))
                .collect(ArrayList::new, ((lists, flyOrders) -> {
                    if (!flyOrders.isEmpty()) lists.add(flyOrders);
                }));
    }

    public Mono<List<List<FlyOrderDTO>>> findAllBestWaysBetweenCities(Integer fromId, Integer toId){
        return orderService.findAllBetweenCities(fromId, toId)
                .defaultIfEmpty(new FlyOrder())
                .flatMap(x->{
                    if (x.getId()!=null) return orderDTOService.findById(x.getId());
                    else return Mono.just(new FlyOrderDTO());
                })
                .map(x->{
                    List<FlyOrderDTO> list=new ArrayList<>();
                    if (x.getId()!=null) list.add(x);
                    return list;
                })
                .concatWith(findTheCheapestWayBetweenCities(fromId, toId))
                .concatWith(findTheFastestWayBetweenCities(fromId, toId))
                .collect(ArrayList::new, ((lists, flyOrders) -> {
                    if (!flyOrders.isEmpty()) lists.add(flyOrders);
                }));
    }
}
