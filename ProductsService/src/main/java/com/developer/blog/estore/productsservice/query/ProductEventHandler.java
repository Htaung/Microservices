package com.developer.blog.estore.productsservice.query;

import com.developer.blog.estore.productsservice.core.data.ProductEntity;
import com.developer.blog.estore.productsservice.core.data.ProductRepository;
import com.developer.blog.estore.productsservice.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {

	@Autowired
	private ProductRepository productRepository;

	@EventHandler
	public void on(ProductCreatedEvent productCreatedEvent){
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(productCreatedEvent, productEntity);
		productRepository.save(productEntity);
	}
}
