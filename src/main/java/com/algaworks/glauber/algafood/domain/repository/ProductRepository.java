package com.algaworks.glauber.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.Product;
import com.algaworks.glauber.algafood.domain.model.Restaurant;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long> {

	@Query("from Product p where p.restaurant.id = :restaurantId and p.id = :productId")
	Optional<Product> findProductByRestaurantIdAndProductId(Long restaurantId, Long productId);
	
	List<Product> findByRestaurant(Restaurant restaurant);
}
