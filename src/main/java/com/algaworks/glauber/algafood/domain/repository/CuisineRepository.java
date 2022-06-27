package com.algaworks.glauber.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.Cuisine;

@Repository
public interface CuisineRepository extends CustomJpaRepository<Cuisine, Long> {
	
	List<Cuisine> findByNameContaining(String name);
	Optional<Cuisine> findByName(String name);
	boolean existsByName(String name);
}
