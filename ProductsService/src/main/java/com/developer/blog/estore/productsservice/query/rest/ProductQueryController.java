package com.developer.blog.estore.productsservice.query.rest;

import com.developer.blog.estore.productsservice.query.FindProductQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping
	public List<ProductRestModel> getProducts(){
		/*
			Send the query to query bus => routed to query handler
			query handler use jpa repository and query the product database
		 */
		FindProductQuery findProductQuery = new FindProductQuery();
		List<ProductRestModel> products = queryGateway.query(findProductQuery,
				ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
		return  products;
	}
}
