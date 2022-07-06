package com.developer.blog.estore.productsservice.command.interceptor;

import com.developer.blog.estore.productsservice.command.CreateProductCommand;
import org.apache.commons.lang.StringUtils;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
	private static final Logger logger = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
		return (index, command) -> {
			logger.info("Intercepted command " + command.getPayloadType());
			if(CreateProductCommand.class.equals(command.getPayloadType())){
				CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
				if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0){
					throw new IllegalArgumentException("Price cannot be less than or equal to zero");
				}

				if(StringUtils.isBlank(createProductCommand.getTitle())){
					throw new IllegalArgumentException("Title should not be empty");
				}
			}
			return  command;
		};
	}
}
