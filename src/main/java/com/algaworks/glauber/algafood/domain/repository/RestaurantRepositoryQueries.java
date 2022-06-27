package com.algaworks.glauber.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.glauber.algafood.domain.model.Restaurant;

public interface RestaurantRepositoryQueries {

	List<Restaurant> find(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal);
	List<Restaurant> findComFreteGratis(String name);

}