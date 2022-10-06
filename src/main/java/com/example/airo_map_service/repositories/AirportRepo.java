package com.example.airo_map_service.repositories;

import com.example.airo_map_service.domain.Airport;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface AirportRepo extends R2dbcRepository<Airport, Integer> {
    Flux<Airport> findAllByCityId(Integer id);
    @Query("select a.id, a.title, a.preview, a.city_id, a.active and c.active and co.active as active " +
            "from (airport a join city c on c.id=a.city_id) join country co on co.id=c.country_id where  a.active and c.active and co.active")
    Flux<Airport> findAllWithActive();
    @Query("select a.id, a.title, a.preview, a.city_id, a.active and c.active and co.active as active " +
            "from (airport a join city c on c.id=a.city_id) join country co on co.id=c.country_id where co.id=:id and a.active and c.active and co.active")
    Flux<Airport> findAllByCountryId(@Param("id") Integer id);
    Flux<Airport> findAllByTitleLike(String title);
}
