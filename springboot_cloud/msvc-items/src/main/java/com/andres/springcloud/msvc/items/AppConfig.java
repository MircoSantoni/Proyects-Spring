package com.andres.springcloud.msvc.items;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {

    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> customizerCircuitBreaker() {
        return ( factory) -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig
                    .custom()
                    .slidingWindowSize(10) // tamano de la ventana para calcular los fallos
                    .waitDurationInOpenState(Duration.ofSeconds(100L)) // duracion del estado abierto
                    .permittedNumberOfCallsInHalfOpenState(5)// la cantidad de llamadas en estado semi-
                    .failureRateThreshold(60) // porcentaje de fallos para que se abra 
                    .slowCallDurationThreshold(Duration.ofSeconds(2L)) // tiempo en el que va a ocurrir una llamada lenta
                    .slowCallRateThreshold(50)// porcentaje de llamadas lentas para abrir el 
                    .build()) 
                    .timeLimiterConfig(TimeLimiterConfig.custom() // configuracion del timeOut
                    .timeoutDuration(Duration.ofSeconds(10L)) // duracion del timeout. este tiene mas jerarquia que slowCall
                    .build())
                    .build();
        });
    }
}
