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

import com.algaworks.glauber.algafood.api.assembler.UserModelAssembler;
import com.algaworks.glauber.algafood.api.model.UserModel;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/responsibles")
public class RestaurantUserResponsibleController {
	
	@Autowired
	private UserModelAssembler userModelAssembler;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@GetMapping
	public List<UserModel> index(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		
		return userModelAssembler.toCollectionModel(restaurant.getResponsibles());
	}
	
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addUserResponsible(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantRegistrationService.addUserResponsible(restaurantId, userId);
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeUserResponsible(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantRegistrationService.removeResponsable(restaurantId, userId);
	}
}
