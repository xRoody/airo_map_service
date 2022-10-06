package com.example.airo_map_service.repositories;

import com.example.airo_map_service.domain.FlyOrder;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface OrderRepo extends R2dbcRepository<FlyOrder, Integer> {
    @Query("select o.id, o.from_id, o.to_id, o.from_date, o.to_date, o.price, o.plane_num, o.company_id from (airport a join city c on c.id=a.city_id) join country co on co.id=c.country_id join fly_order o on a.id=o.to_id where  a.active and c.active and co.active and from_date>CURRENT_DATE and from_id=:fromId and to_id=:toId")
    Flux<FlyOrder> findAllBetweenAirports(Integer fromId, Integer toId);
    @Query("select o.id, o.from_id, o.to_id, o.from_date, o.to_date, o.price, o.plane_num, o.company_id from (airport a join city c on c.id=a.city_id) join country co on co.id=c.country_id join fly_order o on a.id=o.to_id where  a.active and c.active and co.active and from_date>CURRENT_DATE and from_id in(select a.id from airport a join city c on a.city_id = c.id where c.id=:fromId) and to_id in(select a.id from airport a join city c on a.city_id = c.id where c.id=:toId)")
    Flux<FlyOrder> findAllBetweenCities(Integer fromId, Integer toId);
}
