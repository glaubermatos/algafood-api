package com.algaworks.glauber.algafood.infrastructure.repository;

import static com.algaworks.glauber.algafood.infrastructure.repository.specification.RestaurantSpecifications.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepositoryQueries;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired @Lazy
	private RestaurantRepository restaurantRepository;
	
	@Override
	public List<Restaurant> find(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Restaurant> criteriaQuery = builder.createQuery(Restaurant.class);		
		Root<Restaurant> root = criteriaQuery.from(Restaurant.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasLength(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}
		
		if (freightRateInitial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("freightRate"), freightRateInitial));
		}
		
		if (freightRateFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("freightRate"), freightRateFinal));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurant> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();

	}

	@Override
	public List<Restaurant> findComFreteGratis(String name) {
		return restaurantRepository.findAll(comFreteGratis().and(comNomeSemelhante(name)));
	}
	
//	@Override
//	public List<Restaurant> find(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal) {
//		StringBuilder jpql = new StringBuilder("from Restaurant where 0 = 0 ");
//		var queryParameters = new HashMap<String, Object>();
//		
//		if (StringUtils.hasLength(name)) {
//			jpql.append("and name like :name ");
//			queryParameters.put("name", "%" + name + "%");
//		}
//		
//		if (freightRateInitial != null) {
//			jpql.append("and freightRate >= :freightRateInitial ");
//			queryParameters.put("freightRateInitial", freightRateInitial);
//		}
//		
//		if (freightRateFinal != null) {
//			jpql.append("and freightRate <= :freightRateFinal ");
//			queryParameters.put("freightRateFinal", freightRateFinal);
//		}		
//		
//		TypedQuery<Restaurant> query = entityManager.createQuery(jpql.toString(), Restaurant.class);
//		
//		queryParameters.forEach((key, value) -> {
//			System.out.println(key);
//			query.setParameter(key, value);
//		});
//		
//		return query.getResultList();
//	}
}
 