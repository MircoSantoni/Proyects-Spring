package com.mirco.springcloud.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
@SpringBootApplication
@EnableDiscoveryClient
public class MsvcGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcGatewayApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerConfig() {
		return route("msvc-products")
			.route(path("/api/products/**"), http())
			.before(stripPrefix(2))
			.filter((request, next) -> {
				ServerRequest modfiedRequest = ServerRequest.from(request).header("message-request", "Algun message al request").build();
				ServerResponse response = next.handle(modfiedRequest);

				response.headers().add("Message-response", "Algun message desde el response");

				return response;
			})
			.filter(lb("msvc-products"))
			.filter(circuitBreaker(config -> config
			.setId("products")
			.setStatusCodes("500")
			.setFallbackPath("forward:/api/products/5")))
			.build();
	}

}
