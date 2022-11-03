package com.assignment.ordersservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class OrdersRepository implements JpaRepository<OrderEntity, String> {}
