package com.algaworks.glauber.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
