package com.assignment.ordersservice.data;

import com.assignment.ordersservice.command.CreateOrderCommand;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 405396052037923009L;

	@Id
	@Column(unique = true)
	public String orderId;

	private String productId;
	private String userId;
	private int quantity;
	private String addressId;
	@Enumerated(EnumType.STRING)
	private CreateOrderCommand.OrderStatus orderStatus;
}
