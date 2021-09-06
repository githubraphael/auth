package com.phiz.common.auth.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
public class AuthConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
