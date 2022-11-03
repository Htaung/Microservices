package com.assignment.ordersservice.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderRestModel {
	private String productId;
	private int quantity;
	private String addressId;
}
