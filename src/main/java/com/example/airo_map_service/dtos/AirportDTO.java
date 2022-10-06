package com.example.airo_map_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AirportDTO {
    private Integer id;
    private String title;
    private String preview;
    private CityDTO cityDTO;
    private Boolean active;
}
