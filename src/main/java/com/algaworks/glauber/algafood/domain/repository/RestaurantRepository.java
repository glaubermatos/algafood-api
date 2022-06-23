package com.algaworks.glauber.algafood.domain.repository;

import java.util.List;

import com.algaworks.glauber.algafood.domain.model.Restaurant;

public interface RestaurantRepository {

	List<Restaurant> listar();
	Restaurant buscar(Long id);
	Restaurant salvar(Restaurant restaurant);
	void remove(Restaurant restaurant); 
}
