package com.developer.blog.estore.productsservice.query;

import com.developer.blog.estore.productsservice.core.data.ProductEntity;
import com.developer.blog.estore.productsservice.core.data.ProductRepository;
import com.developer.blog.estore.productsservice.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {

	@Autowired
	private ProductRepository productRepository;

	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception ex)throws Exception{
		throw ex;
	}


	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException ex){
		//log error msg
	}

	@EventHandler
	public void on(ProductCreatedEvent productCreatedEvent)throws Exception{
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(productCreatedEvent, productEntity);
		try{
			productRepository.save(productEntity);
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}

		if(true) throw new Exception("Forcing event handler in event handler method");
	}
}
