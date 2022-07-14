package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.ProductInputDisassembler;
import com.algaworks.glauber.algafood.api.assembler.ProductModelAssembler;
import com.algaworks.glauber.algafood.api.model.ProductModel;
import com.algaworks.glauber.algafood.api.model.input.ProductInput;
import com.algaworks.glauber.algafood.domain.model.Product;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.ProductRepository;
import com.algaworks.glauber.algafood.domain.service.ProductRegistrationService;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductModelAssembler productModelAssembler;
	
	@Autowired
	private ProductInputDisassembler productInputDisassembler;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private ProductRegistrationService productRegistrationService;
	
	@GetMapping
	public List<ProductModel> index(@PathVariable Long restaurantId, @RequestParam(value = "include-inactive", required = false) boolean includeInactive) {
		Restaurant restaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		
		List<Product> products = null;
		
		if (includeInactive) {
			products = productRepository.findAllByRestaurant(restaurant);
		} else {
			products = productRepository.findByRestaurantActives(restaurant);
		}
		
		
		return productModelAssembler.toCollectionModel(products); 
	}
	
	@GetMapping("/{productId}")
	public ProductModel show(@PathVariable Long restaurantId, @PathVariable Long productId) {
		return productModelAssembler.toModel(productRegistrationService.findProductByIdAndRestaurantOrElseThrow(restaurantId, productId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductModel create(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		
		Product product = productInputDisassembler.toDomainObject(productInput);
		product.setRestaurant(restaurant);
		
		return productModelAssembler.toModel(productRegistrationService.save(product)); 
	}
	
	@PutMapping("/{productId}")
	public ProductModel update(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductInput productInput) {
//		restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		Product currentProduct = productRegistrationService.findProductByIdAndRestaurantOrElseThrow(restaurantId, productId);
		
		productInputDisassembler.copyToDomainObject(productInput, currentProduct);
		
		return productModelAssembler.toModel(productRegistrationService.save(currentProduct));
	}
}
