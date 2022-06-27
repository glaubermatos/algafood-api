package com.algaworks.glauber.algafood.infrastructure.repository.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.glauber.algafood.domain.model.Restaurant;

public class RestaurantSpecifications {

	public static Specification<Restaurant> comFreteGratis() {
		return (root, query, builder) -> builder.equal(root.get("freightRate"), BigDecimal.ZERO);
	}

	public static Specification<Restaurant> comNomeSemelhante(String name) {
		return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
	}
}
