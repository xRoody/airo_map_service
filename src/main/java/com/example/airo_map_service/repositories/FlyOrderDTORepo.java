package com.example.airo_map_service.repositories;

import com.example.airo_map_service.domain.Company;
import com.example.airo_map_service.domain.Country;
import com.example.airo_map_service.dtos.AirportDTO;
import com.example.airo_map_service.dtos.CityDTO;
import com.example.airo_map_service.dtos.FlyOrderDTO;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class FlyOrderDTORepo {
    private DatabaseClient databaseClient;

    public FlyOrderDTORepo(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Mono<FlyOrderDTO> findById(Integer id){
        return databaseClient.sql("select co1.id co1_id, co1.title co1_title, co1.active co1_active, co1.description co1_desc, co1.preview co1_prev,co2.id co2_id, co2.title co2_title, co2.active co2_active, co2.description co2_desc, co2.preview co2_prev, c1.id c1_id, c1.title c1_title, c1.active c1_active, c1.description c1_desc, c1.preview c1_prev, c2.id c2_id, c2.title c2_title, c2.active c2_active, c2.description c2_desc, c2.preview c2_prev, a1.id a1_id, a1.title a1_title, a1.preview a1_prev, a1.active a1_active, a2.id a2_id, a2.title a2_title, a2.preview a2_prev, a2.active a2_active, o.id o_id, o.from_date o_from_date, o.to_date o_to_date, o.price o_price, o.plane_num o_plane, comp.id comp_id, comp.title comp_title, comp.description comp_desc from fly_order o join airport a1 on o.from_id=a1.id join airport a2 on o.to_id=a2.id join city c1 on c1.id=a1.city_id join city c2 on c2.id=a2.city_id join country co1 on co1.id=c1.country_id join country co2 on co2.id=c2.country_id join company comp on comp.id=o.company_id where o.id=:id")
                .bind("id", id)
                .map((row, meta)->{
                    return FlyOrderDTO.builder()
                            .id(row.get("o_id", Integer.class))
                            .from(AirportDTO.builder()
                                    .id(row.get("a1_id", Integer.class))
                                    .title(row.get("a1_title", String.class))
                                    .preview(row.get("a1_prev", String.class))
                                    .active(row.get("a1_active", Boolean.class))
                                    .cityDTO(CityDTO
                                            .builder()
                                            .id(row.get("c1_id", Integer.class))
                                            .title(row.get("c1_title", String.class))
                                            .active(row.get("c1_active", Boolean.class))
                                            .preview(row.get("c1_prev", String.class))
                                            .description(row.get("c1_desc", String.class))
                                            .country(Country
                                                    .builder()
                                                    .id(row.get("co1_id", Integer.class))
                                                    .title(row.get("co1_title", String.class))
                                                    .active(row.get("co1_active", Boolean.class))
                                                    .description(row.get("co1_desc", String.class))
                                                    .preview(row.get("co1_prev", String.class))
                                                    .build()
                                            )
                                            .build()
                                    )
                                    .build()
                            )
                            .to(AirportDTO.builder()
                                    .id(row.get("a2_id", Integer.class))
                                    .title(row.get("a2_title", String.class))
                                    .preview(row.get("a2_prev", String.class))
                                    .active(row.get("a2_active", Boolean.class))
                                    .cityDTO(CityDTO
                                            .builder()
                                            .id(row.get("c2_id", Integer.class))
                                            .title(row.get("c2_title", String.class))
                                            .active(row.get("c2_active", Boolean.class))
                                            .preview(row.get("c2_prev", String.class))
                                            .description(row.get("c2_desc", String.class))
                                            .country(Country
                                                    .builder()
                                                    .id(row.get("co2_id", Integer.class))
                                                    .title(row.get("co2_title", String.class))
                                                    .active(row.get("co2_active", Boolean.class))
                                                    .description(row.get("co2_desc", String.class))
                                                    .preview(row.get("co2_prev", String.class))
                                                    .build()
                                            )
                                            .build()
                                    )
                                    .build()
                            )
                            .fromDate(row.get("o_from_date", LocalDateTime.class))
                            .toDate(row.get("o_to_date", LocalDateTime.class))
                            .price(row.get("o_price", Double.class))
                            .company(Company
                                    .builder()
                                    .id(row.get("comp_id", Integer.class))
                                    .title(row.get("comp_title", String.class))
                                    .description(row.get("comp_desc", String.class))
                                    .build()
                            )
                            .planeNum(row.get("o_plane", String.class))
                            .build();
                })
                .first();
    }
}
