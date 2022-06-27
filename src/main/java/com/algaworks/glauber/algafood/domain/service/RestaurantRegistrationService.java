package com.algaworks.glauber.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	public Restaurant salvar(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		
		Cuisine cuisine = cuisineRepository.findById(cuisineId)
				.orElseThrow(() -> new EntityNotFoundException(String.format("A cozinha de código %d não existe", cuisineId)));
		
		restaurant.setCuisine(cuisine);
		
		return restaurantRepository.save(restaurant);
	}
}
