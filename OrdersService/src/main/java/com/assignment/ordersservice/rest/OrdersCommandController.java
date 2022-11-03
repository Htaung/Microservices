package com.assignment.ordersservice.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/orders")
public class OrdersCommandController {
	@Autowired
	private CommandGateway commandGateway;


	@PostMapping
	public String createOrder(@Valid @RequestBody CreateOrderRestModel createOrderRestModel){

		CreateOrderRestModel createOrder =CreateOrderRestModel.builder()
				.productId(createOrderRestModel.getProductId())
				.quantity(createOrderRestModel.getQuantity())
				.addressId(createOrderRestModel.getAddressId()).build();

		String returnValue = commandGateway.sendAndWait(createOrder);

		System.out.println("returnValue " + returnValue);
		return  returnValue;
	}
}
