package com.algaworks.glauber.algafood.infrastructure.repository.specification;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.glauber.algafood.domain.filter.DailySalesFilter;
import com.algaworks.glauber.algafood.domain.model.Purchase;

public class DailySalesSpecifications {

	public static Specification<Purchase> usingFilter(DailySalesFilter filter) {
		return (root, query, builder) -> {
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getCreatedAtInitial() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtInitial()));
			}
			
			if (filter.getCreatedAtFinal() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtFinal()));
			}
			
			if (filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant.id"), filter.getRestaurantId()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
			
			
		};
		
	}

}
