package com.algaworks.glauber.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.ProductNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Product;
import com.algaworks.glauber.algafood.domain.repository.ProductRepository;

@Service
public class ProductRegistrationService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public Product findProductByIdAndRestaurantOrElseThrow(Long restaurantId, Long productId) {
		return productRepository.findProductByRestaurantIdAndProductId(restaurantId, productId)
				.orElseThrow(() -> new ProductNotFoundException(String
						.format("Produto de id %d não está cadastrado para o restaurante de id %d", productId, restaurantId)));
	}
	
	
	
//	public Product getProduct(Long restaurantId, Long productId) {
//		findRestaurantByIdOrElseThrow(restaurantId);
//		Optional<Product> productOptional = productRepository.findProductByRestaurantIdAndProductId(restaurantId, productId);
//		
//		if (productOptional.isEmpty()) {
//			throw new ProductNotFoundException(String.format("Produto de id %d não está cadastrado para o restaurante de id %d", productId, restaurantId));
//		}
//		
//		return productOptional.get();
//	}
}
