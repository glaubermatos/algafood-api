package com.algaworks.glauber.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository 
		extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, 
		JpaSpecificationExecutor<Restaurant> {
	
//	@Query("from Restaurant r where r.name like %:name% and r.cuisine.id = :id")
	List<Restaurant> consultarPorNome(String name, @Param("id") Long cuisineId);
	
	List<Restaurant> findByFreightRateBetween(BigDecimal freightRateInitial, BigDecimal freightRateFinal);
	Optional<Restaurant> findFirstByNameContaining(String name);
	List<Restaurant> findTop2ByNameContaining(String name);
	int countByCuisineId(Long cuisineId);
}
 