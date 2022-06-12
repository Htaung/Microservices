package com.developer.blog.estore.productsservice.query;

import com.developer.blog.estore.productsservice.core.data.ProductEntity;
import com.developer.blog.estore.productsservice.core.data.ProductRepository;
import com.developer.blog.estore.productsservice.query.rest.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductQueryHandler {
	@Autowired
	private ProductRepository productRepository;

	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductQuery query){
		List<ProductRestModel> productRestModels = new ArrayList<>();
		List<ProductEntity> storeProducts = productRepository.findAll();

		storeProducts.stream().forEach(x->{
			ProductRestModel productRestModel = new ProductRestModel();
			BeanUtils.copyProperties(x, productRestModel);
			productRestModels.add(productRestModel);
		});

		return  productRestModels;
	}
}
