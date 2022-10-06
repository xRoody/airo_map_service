package com.example.airo_map_service.repositories;

import com.example.airo_map_service.services.impl.graph.RoadNode;
import lombok.AllArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;


@Repository
@AllArgsConstructor
public class RoadNodeRepo {
    private DatabaseClient databaseClient;

    public Flux<RoadNode> findAllNodesFrom(Integer fromId) {
        return databaseClient.sql("select  o.id ord, o.to_id id, o.price price, o.to_date to_date, o.from_date from_date from country co join city c on co.id=c.country_id join airport a on c.id=a.city_id join fly_order o on a.id=o.from_id where o.from_id=:from and a.active and co.active and c.active")
                .bind("from", fromId)
                .map((row, meta) -> RoadNode.builder()
                        .cur(row.get("id", Integer.class))
                        .price(row.get("price", Double.class))
                        .toDate(row.get("to_date", LocalDateTime.class))
                        .orderId(row.get("ord", Integer.class))
                        .fromDate(row.get("from_date", LocalDateTime.class))
                        .build()
                )
                .all();
    }
}
