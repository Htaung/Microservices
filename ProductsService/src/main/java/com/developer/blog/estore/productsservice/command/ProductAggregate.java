package com.developer.blog.estore.productsservice.command;

import com.developer.blog.estore.productsservice.core.events.ProductCreatedEvent;
import org.apache.commons.lang.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate(){
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand){
        //Validate Create Product command
        if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price cannot be less than or equal to zero");
        }

        if(StringUtils.isBlank(createProductCommand.getTitle())){
            throw new IllegalArgumentException("Title should not be empty");
        }

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);

	    /** product created event will not be persisted in event store
	     * axon framework doesn't persist immediately
	     * txn will be roll back and none of event will be persisted
	     * axon framework will rob this error into command execution exception into query execution exception
	     * page for error handling is decicated handler execution exception become clear in distributed app environment
	     * eg. app for dealing with command and other app is dealing with query side do app segregation
	     * we loose certainly that both app can access the same process and to support and encourage this
	     * axon will generally find any exception which is a result of command or query handling
	     */
	    //if(true) throw new Exception("An error took place in CreateProductCommand @CommandHandler");
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.title = productCreatedEvent.getTitle();
        this.quantity = productCreatedEvent.getQuantity();
    }
}
