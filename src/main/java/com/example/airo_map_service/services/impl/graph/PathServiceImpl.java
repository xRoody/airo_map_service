package com.example.airo_map_service.services.impl.graph;

import com.example.airo_map_service.services.PathService;
import com.example.airo_map_service.services.GraphService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;


@Component
public class PathServiceImpl implements PathService {
    private final Comparator<RoadNode> priceComparator = (r1, r2) -> {
        BigDecimal price1 = BigDecimal.valueOf(0);
        BigDecimal price2 = BigDecimal.valueOf(0);
        while (r1 != null) {
            price1 = price1.add(BigDecimal.valueOf(r1.getPrice()));
            r1 = r1.getPrev();
        }
        while (r2 != null) {
            price2 = price2.add(BigDecimal.valueOf(r2.getPrice()));
            r2 = r2.getPrev();
        }
        return price1.compareTo(price2);
    };
    private final Comparator<RoadNode> timeComparator= Comparator.comparing(RoadNode::getToDate);
    private final GraphService graphStorage;

    public PathServiceImpl(GraphService graphStorage) {
        this.graphStorage = graphStorage;
    }

    @Override
    public Mono<RoadNode> findTheCheapestWayBetweenAirports(Integer fromId, Integer toId){
        return Flux.just(fromId)
                .map(RoadNode::new)
                .collect(() -> new PriorityQueue<>(priceComparator), (PriorityQueue::add))
                .flatMap(queue -> trueFindAlgorithm(queue, new HashSet<>(), Flux.just(toId)));
    }

    @Override
    public Mono<RoadNode> findTheCheapestWayBetweenCity(Integer fromId, Integer toId) {
        if (!graphStorage.isActiveCity(fromId) || !graphStorage.isActiveCity(toId)) return Mono.empty();
        return graphStorage.getAirportsIdsByCityId(fromId)
                .map(RoadNode::new)
                .collect(() -> new PriorityQueue<>(priceComparator), (PriorityQueue::add))
                .flatMap(queue -> trueFindAlgorithm(queue, new HashSet<>(), graphStorage.getAirportsIdsByCityId(toId)));
    }

    @Override
    public Mono<RoadNode> findTheFastestWayBetweenAirports(Integer fromId, Integer toId){
        return Flux.just(fromId)
                .map(RoadNode::new)
                .collect(() -> new PriorityQueue<>(timeComparator), (PriorityQueue::add))
                .flatMap(queue -> trueFindAlgorithm(queue, new HashSet<>(), Flux.just(toId)));
    }

    @Override
    public Mono<RoadNode> findTheFastestWayBetweenCity(Integer fromId, Integer toId) {
        if (!graphStorage.isActiveCity(fromId) || !graphStorage.isActiveCity(toId)) return Mono.empty();
        return graphStorage.getAirportsIdsByCityId(fromId)
                .map(RoadNode::new)
                .collect(() -> new PriorityQueue<>(timeComparator), (PriorityQueue::add))
                .flatMap(queue -> trueFindAlgorithm(queue, new HashSet<>(), graphStorage.getAirportsIdsByCityId(toId)));
    }

    private Mono<RoadNode> trueFindAlgorithm(PriorityQueue<RoadNode> queue, Set<Integer> visited, Flux<Integer> toIdSet) {
        RoadNode n = queue.poll();
        if (n == null) return Mono.empty();
        return Mono.just(n).flatMap(node -> toIdSet.any(k->k.equals(node.getCur())).flatMap(b->{
           if (b) return Mono.just(node);
           if (visited.contains(node.getCur())) return trueFindAlgorithm(queue, visited, toIdSet);
           visited.add(node.getCur());
           return graphStorage
                   .findAllNodesFrom(node.getCur())
                   .filter(x -> (node.getToDate() == null || x.getFromDate().isAfter(node.getToDate().plusMinutes(30L))) && !visited.contains(x.getCur()))
                   .collect(() -> new PriorityQueue<>(priceComparator), (q, x) -> {
                       x.setPrev(node);
                       q.add(x);
                   })
                   .flatMap(x -> {
                       queue.addAll(x);
                       return trueFindAlgorithm(queue, visited, toIdSet);
                   });
       }));
    }
}
