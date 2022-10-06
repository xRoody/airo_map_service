package com.example.airo_map_service.dtos;

import com.example.airo_map_service.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlyOrderDTO {
    private Integer id;
    private AirportDTO from;
    private AirportDTO to;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Double price;
    private Company company;
    private String planeNum;
}
