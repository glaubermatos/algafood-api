package com.algaworks.glauber.algafood.infrastructure.repository.specification;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.glauber.algafood.domain.model.Restaurant;

public class RestauranteComFreteGratisSpecification implements Specification<Restaurant> {

	private static final long serialVersionUID = 1L;

	@Override
	public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.equal(root.get("freightRate"), BigDecimal.ZERO);
	}

}
