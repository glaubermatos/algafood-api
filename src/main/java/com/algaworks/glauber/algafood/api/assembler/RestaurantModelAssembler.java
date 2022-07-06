package com.algaworks.glauber.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.RestaurantModel;
import com.algaworks.glauber.algafood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestaurantModel toModel(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantModel.class);
	}
	
	public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream()
				.map(restaurant -> toModel(restaurant))
				.collect(Collectors.toList());
	}
}
