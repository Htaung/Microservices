package com.assignment.ordersservice.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {
	public final String orderId;
	private final String userId;
	private final String productId;
	private final int quantity;
	private final String addressId;
	private final OrderStatus orderStatus;

	public enum OrderStatus {
		CREATED, APPROVED, REJECTED
	}
}
