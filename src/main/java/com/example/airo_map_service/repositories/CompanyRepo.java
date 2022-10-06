package com.example.airo_map_service.repositories;

import com.example.airo_map_service.domain.Company;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CompanyRepo extends R2dbcRepository<Company, Integer> {
}
