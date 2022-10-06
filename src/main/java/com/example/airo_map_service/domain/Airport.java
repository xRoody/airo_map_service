package com.example.airo_map_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "airport")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Airport {
    @Id
    private Integer id;
    private String title;
    private String preview;
    @Column("city_id")
    private Integer cityId;
    private Boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(id, airport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
