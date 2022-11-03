package com.assignment.orderapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApiGatewayApplication.class, args);
	}
}
