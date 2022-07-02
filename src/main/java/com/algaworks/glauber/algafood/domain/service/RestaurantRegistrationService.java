package com.algaworks.glauber.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;;
	
	public Restaurant salvar(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		
		Cuisine cuisine = cuisineRegistrationService.findCuisineByIdOrElseThrow(cuisineId);
		
		restaurant.setCuisine(cuisine);
		
		return restaurantRepository.save(restaurant);
	}
	
	public Restaurant findRestaurantByIdOrElseThrow(Long id) {
		try {
			return restaurantRepository.findByIdOrElseThrow(id);
		} catch (EntityNotFoundException e) {
			throw new RestaurantNotFoundException(id);
		}
	}
}
