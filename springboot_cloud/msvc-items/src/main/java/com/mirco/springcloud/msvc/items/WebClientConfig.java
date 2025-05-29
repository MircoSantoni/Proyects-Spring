package com.mirco.springcloud.msvc.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // este valor se guardaba en el aplciation properties y no se pq no me acordaba como se hacia 
    // @Value("${config.baseurl.endpoint.msvc-products}")
    // private String url;

    // esta primer implementacion configuramos el builder del webclient para despues usarlo, spring maneja el load balancing
    // @Bean
    // @LoadBalanced
    // WebClient.Builder webClient() {
    //     return WebClient.builder().baseUrl(url);
    // }


    // Esta implementacion devuelve la isntancia de webclient lista para usarse
    // esta implementado para propagar el contexto de trazas de micrometer tracing en este caso
    @Bean
    WebClient webClient(WebClient.Builder webClientBuilder, ReactorLoadBalancerExchangeFilterFunction lbFunction, 
    @Value("${config.baseurl.endpoint.msvc-products}") String url) {
        return webClientBuilder.baseUrl(url).filter(lbFunction).build();
    }
}
