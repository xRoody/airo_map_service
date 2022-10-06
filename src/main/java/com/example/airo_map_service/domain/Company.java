package com.example.airo_map_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("company")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Company {
    @Id
    private Integer id;
    private String title;
    private String description;
}
