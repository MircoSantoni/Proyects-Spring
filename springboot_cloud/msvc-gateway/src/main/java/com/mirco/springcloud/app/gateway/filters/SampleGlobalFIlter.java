package com.mirco.springcloud.app.gateway.filters;

import java.util.List;
import java.util.Optional;

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
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { // <= Esto no funciona
        logger.info("Ejecutando el filtro antes del request");

        exchange.getRequest().mutate().headers(h -> h.add("Token", "Valor de token random")).build();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("Ejecutando el filtro despues del request");


            List<String> tokenList = exchange.getRequest().getHeaders().get("Token");
            if (tokenList != null && !tokenList.isEmpty()) {
                logger.info("Token: " + tokenList.get(0));
            } else {
                logger.warn("Token header no presente.");
            }

            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("Token")).ifPresent(value -> {
                logger.info("Token: " + value);
                exchange.getResponse().getHeaders().add("Token ", value);
            });
            


            exchange.getResponse().getCookies().add("Color", ResponseCookie.from("Color", "Red").build());
            exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }   
        //     @Override
        // public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //     logger.info("Ejecutando el filtro antes del request");

        //     // Agregar header antes del request
        //     ServerWebExchange mutatedExchange = exchange.mutate()
        //         .request(exchange.getRequest().mutate()
        //             .header("Token", "Valor de token random")
        //             .build())
        //         .build();

        //     return chain.filter(mutatedExchange).then(Mono.fromRunnable(() -> {
        //         logger.info("Ejecutando el filtro despues del request");

        //         // Ahora podés loguear o setear headers en la response
        //         Optional.ofNullable(mutatedExchange.getRequest().getHeaders().getFirst("Token")).ifPresent(value -> {
        //             logger.info("Token: " + value);
        //             mutatedExchange.getResponse().getHeaders().add("Token", value);
        //         });

        //         mutatedExchange.getResponse().getCookies().add("Color", ResponseCookie.from("Color", "Red").build());
        //         mutatedExchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        //     }));
        // }




    @Override
    public int getOrder() {
        return 100;
    }
    
}
