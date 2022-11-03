package com.assignment.ordersservice.event;

import com.assignment.ordersservice.data.OrderEntity;
import com.assignment.ordersservice.data.OrdersRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsHandler {

	@Autowired
	private OrdersRepository ordersRepository;

	@EventHandler
	public void on(OrderCreatedEvent orderCreatedEvent)throws Exception{
		OrderEntity orderEntity = new OrderEntity();
		BeanUtils.copyProperties(orderCreatedEvent, orderEntity);
		try{
			ordersRepository.save(orderEntity);
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}

		//if(true) throw new Exception("Forcing event handler in event handler method");
	}
}
