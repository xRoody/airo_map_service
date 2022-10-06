package com.example.airo_map_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "country")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Country {
    @Id
    private Integer id;
    private String title;
    private String description;
    private String preview;
    private Boolean active;
}
