package com.example.airo_map_service;

import com.example.airo_map_service.facades.PathFacade;
import com.example.airo_map_service.services.GraphService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;


@SpringBootApplication
public class AiroMapServiceApplication {
    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(AiroMapServiceApplication.class, args);
        


    }
}
