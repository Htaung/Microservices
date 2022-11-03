package com.assignment.ordersservice.event;

import com.assignment.ordersservice.command.CreateOrderCommand;
import lombok.Data;

@Data
public class OrderCreatedEvent {
	private String orderId;
	private String productId;
	private String userId;
	private int quantity;
	private String addressId;
	private CreateOrderCommand.OrderStatus orderStatus;
}
