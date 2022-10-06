package com.example.airo_map_service.repositories;

import com.example.airo_map_service.domain.City;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CityRepo extends R2dbcRepository<City, Integer> {
    @Query("select c.id, c.title, c.preview, c.description, c.country_id, c.active and co.active as active " +
            "from city c join country co on co.id=c.country_id where c.active and co.active and co.id=:id")
    Flux<City> findAllByCountryIdAndActive (Integer id);
    @Query("select c.id, c.title, c.preview, c.description, c.country_id, c.active and co.active as active " +
            "from city c join country co on co.id=c.country_id where c.active and co.active")
    Flux<City> findAllWithActive();
}
