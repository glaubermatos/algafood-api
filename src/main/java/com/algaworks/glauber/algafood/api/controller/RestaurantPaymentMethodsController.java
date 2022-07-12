package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.PaymentMethodModelAssembler;
import com.algaworks.glauber.algafood.api.model.PaymentMethodModel;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payment-methods")
public class RestaurantPaymentMethodsController {
	
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private PaymentMethodModelAssembler paymentMethodModelAssembler;
	
	@GetMapping
	public List<PaymentMethodModel> index(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		
		return paymentMethodModelAssembler.toCollectionModel(restaurant.getPaymentMethods());
	}

	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addPaymentMethod(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.associarFormaPagamento(restaurantId, paymentMethodId);
	}
	
	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removePaymentMethod(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.desassociarFormaPagamento(restaurantId, paymentMethodId);
	}
}
