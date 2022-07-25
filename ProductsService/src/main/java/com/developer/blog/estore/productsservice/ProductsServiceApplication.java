package com.developer.blog.estore.productsservice;

import com.developer.blog.estore.productsservice.command.interceptor.CreateProductCommandInterceptor;
import com.developer.blog.estore.productsservice.core.errorhandling.ProductServiceEventErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

@EnableEurekaClient
@SpringBootApplication
public class ProductsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsServiceApplication.class, args);
    }

    @Autowired
    public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus){
	    commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
    }

    @Autowired
    public void configure(EventProcessingConfigurer config){
    	//for specific processing group
		config.registerListenerInvocationErrorHandler("product-group", conf-> new ProductServiceEventErrorHandler());

	    //config.registerListenerInvocationErrorHandler("product-group", conf-> PropagatingErrorHandler.instance());
    }
}
