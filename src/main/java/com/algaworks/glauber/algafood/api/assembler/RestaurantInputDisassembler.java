package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.input.RestaurantInput;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.model.Restaurant;

@Component
public class RestaurantInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurant toDomainObject(RestaurantInput input) {
		return modelMapper.map(input, Restaurant.class);
	}
	
	public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
		//Para evitar lançar exceção Caused by: org.hibernate.HibernateException: identifier of an 
		//instance of com.algaworks.glauber.algafood.domain.model.Cuisine was altered from 1 to 10
		restaurant.setCuisine(new Cuisine());
		
		if (restaurant.getAddress() != null) {
			restaurant.getAddress().setCity(new City());
		}
		
		modelMapper.map(restaurantInput, restaurant);
	}
}
