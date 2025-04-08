package com.mirco.springcloud.app.gateway.msvc_gatway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MsvcGatwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcGatwayApplication.class, args);
	}

}
