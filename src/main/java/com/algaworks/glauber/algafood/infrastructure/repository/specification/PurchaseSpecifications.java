package com.algaworks.glauber.algafood.infrastructure.repository.specification;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.glauber.algafood.domain.filter.PurchaseFilter;
import com.algaworks.glauber.algafood.domain.model.Purchase;

public class PurchaseSpecifications {

	public static Specification<Purchase> usingFilter(PurchaseFilter filter) {
		return (root, query, builder) -> {

			//esse if é para evitar que seja feita o fetch em um select count
			if (Purchase.class.equals(query.getResultType())) {
				//resolve o problema do N+1 implementando o join fetch com spacification
				root.fetch("restaurant").fetch("cuisine");
				root.fetch("client");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			//adicionar predicates conforme o PurchaseFilter no array de predicates
			if (filter.getClientId() != null) {
				predicates.add(builder.equal(root.get("client"), filter.getClientId()));
			}
			
			if (filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
			}
			
			if (filter.getCreatedAtInitial() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtInitial()));
			}
			
			if (filter.getCreatedAtFinal() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtFinal()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
