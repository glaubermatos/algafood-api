package com.algaworks.glauber.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.filter.DailySalesFilter;
import com.algaworks.glauber.algafood.domain.model.Purchase;
import com.algaworks.glauber.algafood.domain.model.PurchaseStatus;
import com.algaworks.glauber.algafood.domain.model.dto.DailySales;
import com.algaworks.glauber.algafood.domain.service.SalesQueryService;

@Repository
public class DailySalesQueryServiceImpl implements SalesQueryService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DailySales> findDailySales(DailySalesFilter filter, String timeOffSet) {
		var builder = entityManager.getCriteriaBuilder();
		var query = builder.createQuery(DailySales.class);
		var root = query.from(Purchase.class);
		
		var functionConvertTzCreatedAt = builder
				.function("convert_tz", Date.class,
						root.get("createdAt"),
						builder.literal("+00:00"), 
						builder.literal(timeOffSet));
		
		var functionDateCreatedAt = builder.function("date", Date.class, functionConvertTzCreatedAt);
		
		var selection = builder.construct(DailySales.class, 
			functionDateCreatedAt, 
			builder.count(root.get("id")),
			builder.sum(root.get("totalAmount"))
			);
		
		var predicates = new ArrayList<Predicate>();
		
		if (filter.getCreatedAtInitial() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtInitial()));
		}
		
		if (filter.getCreatedAtFinal() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtFinal()));
		}
		
		if (filter.getRestaurantId() != null) {
			predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
		}
		
		predicates.add(root.get("status").in(PurchaseStatus.CONFIRMED, PurchaseStatus.DELIVERED));
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateCreatedAt);
		
		
		
		return entityManager.createQuery(query).getResultList();
	}

}
