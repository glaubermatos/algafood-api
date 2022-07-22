package com.algaworks.glauber.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.Product;
import com.algaworks.glauber.algafood.domain.model.ProductPhoto;
import com.algaworks.glauber.algafood.domain.model.Restaurant;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long>, ProductRepositoryQueries {

	@Query("from Product p where p.restaurant.id = :restaurantId and p.id = :productId")
	Optional<Product> findProductByRestaurantIdAndProductId(Long restaurantId, Long productId);
	
	List<Product> findAllByRestaurant(Restaurant restaurant);
	
	@Query("from Product p where p.restaurant = :restaurant and p.active = true")
	List<Product> findByRestaurantActives(Restaurant restaurant);
	
	@Query("select p from ProductPhoto p join p.product prod where p.product.id = :productId and prod.restaurant.id = :restaurantId")
	Optional<ProductPhoto> findPhotoByRestaurantAndProduct(Long restaurantId, Long productId);
}
