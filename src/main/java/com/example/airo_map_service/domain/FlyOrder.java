package com.example.airo_map_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "fly_order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FlyOrder {
    @Id
    private Integer id;
    @Column("from_id")
    private Integer fromId;
    @Column("to_id")
    private Integer toId;
    @Column("from_date")
    private LocalDateTime fromDate;
    @Column("to_date")
    private LocalDateTime toDate;
    private Double price;
    @Column("plane_num")
    private String planeNum;
    @Column("company_id")
    private Integer companyId;
}
