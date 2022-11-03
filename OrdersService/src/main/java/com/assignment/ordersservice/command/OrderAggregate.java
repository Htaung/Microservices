package com.assignment.ordersservice.command;

import com.assignment.ordersservice.event.OrderCreatedEvent;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class OrderAggregate {

	private String orderId;
	private String productId;
	private String userId;
	private int quantity;
	private String addressId;
	private CreateOrderCommand.OrderStatus orderStatus;

	@EventSourcingHandler
	public void on(OrderCreatedEvent orderCreatedEvent){
		this.orderId = orderCreatedEvent.getOrderId();
		this.productId = orderCreatedEvent.getProductId();
		this.userId = orderCreatedEvent.getUserId();
		this.quantity = orderCreatedEvent.getQuantity();
		this.quantity = orderCreatedEvent.getQuantity();
	}

}
