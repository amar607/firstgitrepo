package com.amar.blog.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class CustomConfigurations {

    // CORS is configured in one place only: SecurityConfig.corsFilter(),
    // driven by the app.cors.allowed-origins property. A second CORS
    // mapping here (previously wildcard "*") was redundant and a likely
    // source of confusing behavior, so it's been removed.

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
