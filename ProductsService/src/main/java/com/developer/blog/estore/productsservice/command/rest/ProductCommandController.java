package com.developer.blog.estore.productsservice.command.rest;

import com.developer.blog.estore.productsservice.command.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductCommandController {

    @Autowired
    private Environment environment;

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductRestModel createProductRestModel){
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .productId(UUID.randomUUID().toString())
                .build();
        String returnValue = "";
        try {
            returnValue = commandGateway.sendAndWait(createProductCommand);
        }catch (Exception e){
            returnValue = e.getLocalizedMessage();
        }

        System.out.println("return value: " + returnValue);
        return returnValue;
    }


    /*@GetMapping
    public String getProduct(){
        return "HTTP Get handled"+ environment.getProperty("local.server.port");
    }

    @PutMapping
    public String updateProduct(){
        return "HTTP Get handled";
    }

    @DeleteMapping
    public String deleteProduct(){
        return "HTTP Get handled";
    }*/
}
