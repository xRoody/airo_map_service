package com.example.airo_map_service.dtos;

import com.example.airo_map_service.domain.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityDTO {
    private Integer id;
    private String title;
    private String description;
    private String preview;
    private Country country;
    private Boolean active;
}
