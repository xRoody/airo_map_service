package com.example.airo_map_service.services.impl.graph;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class RoadNode {
    private Integer cur;
    private Integer orderId;
    private RoadNode prev;
    private Double price;
    private LocalDateTime toDate;
    private LocalDateTime fromDate;

    public RoadNode(Integer cur) {
        this(cur, null, null, 0.0, LocalDateTime.now(), LocalDateTime.now());
    }

    public RoadNode(Integer cur, Integer orderId, RoadNode prev, Double price, LocalDateTime toDate, LocalDateTime fromDate) {
        this.cur = cur;
        this.orderId = orderId;
        this.prev = prev;
        this.price = price;
        this.toDate = toDate;
        this.fromDate=fromDate;
    }
}
