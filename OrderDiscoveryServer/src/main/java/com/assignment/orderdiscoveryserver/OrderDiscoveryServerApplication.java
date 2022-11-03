package com.assignment.orderdiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OrderDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderDiscoveryServerApplication.class, args);
	}
}
