package com.algaworks.glauber.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.repository.CityRepository;

@Component
public class CityRepositoryImpl implements CityRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<City> listar() {
		return entityManager.createQuery("from City", City.class)
				.getResultList();
	}

	@Override
	public City buscar(Long id) {
		return entityManager.find(City.class, id);
	}

	@Transactional
	@Override
	public City salvar(City city) {
		return entityManager.merge(city);
	}

	@Transactional
	@Override
	public void remover(Long cityId) {
		City city = buscar(cityId);
		
		if (city == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		entityManager.remove(city);
	}

}
