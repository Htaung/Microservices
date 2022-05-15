package com.developer.blog.estore.productsservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private Environment environment;

    @PostMapping
    public String createProduct(){
        return "HTTP post handled " + environment.getProperty("local.server.port");
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
