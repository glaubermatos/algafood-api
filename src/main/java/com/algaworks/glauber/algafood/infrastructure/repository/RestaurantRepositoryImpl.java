package com.algaworks.glauber.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Restaurant> listar() {
		return entityManager.createQuery("from Restaurant", Restaurant.class).getResultList();
	}

	@Override
	public Restaurant buscar(Long id) {
		return entityManager.find(Restaurant.class, id);
	}

	@Transactional
	@Override
	public Restaurant salvar(Restaurant restaurant) {
		return entityManager.merge(restaurant);
	}

	@Transactional
	@Override
	public void remove(Restaurant restaurant) {
		restaurant = buscar(restaurant.getId());
		entityManager.remove(restaurant);

	}

}
