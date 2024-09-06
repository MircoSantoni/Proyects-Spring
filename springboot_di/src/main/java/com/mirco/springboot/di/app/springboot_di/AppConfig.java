package com.mirco.springboot.di.app.springboot_di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mirco.springboot.di.app.springboot_di.repositories.ProductRepository;
import com.mirco.springboot.di.app.springboot_di.repositories.ProductRepositoryJson;

@Configuration

public class AppConfig {

    @Bean
    @Primary
     ProductRepository productRepositoryJson() {
        return new ProductRepositoryJson();
    }
}
