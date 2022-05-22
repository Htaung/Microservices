package com.developer.blog.estore.productsservice.rest;

import com.developer.blog.estore.productsservice.command.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private Environment environment;

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel createProductRestModel){
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

    @GetMapping
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
    }
}
