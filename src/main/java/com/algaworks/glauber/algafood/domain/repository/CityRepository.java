package com.algaworks.glauber.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.City;

@Repository
public interface CityRepository extends CustomJpaRepository<City, Long> {

}
