package com.mirco.springcloud.app.gateway.filters;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SampleGlobalFIlter implements GlobalFilter, Ordered{

    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFIlter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Ejecutando el filtro antes del request");

        exchange.getRequest().mutate().headers(h -> h.add("Token", "Valor de token random"));
        
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("Ejecutando el filtro despues del request");

            String token = exchange.getRequest().getHeaders().get("Token").get(0);
            logger.info("Token: " + token);
            
            exchange.getResponse().getCookies().add("Color", ResponseCookie.from("Color", "Red").build());
            exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }
    
}
