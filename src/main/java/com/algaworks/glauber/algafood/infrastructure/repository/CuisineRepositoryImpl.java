package com.algaworks.glauber.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;

@Component
public class CuisineRepositoryImpl implements CuisineRepository {

	@PersistenceContext
	private EntityManager entityManager; 

	public List<Cuisine> listar() {
		TypedQuery<Cuisine> query = entityManager.createQuery("from Cuisine", Cuisine.class);
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public Cuisine salvar(Cuisine cozinha) {		
		return entityManager.merge(cozinha);
	}
	
	@Override
	public Cuisine buscar(Long id) {
		return entityManager.find(Cuisine.class, id);
	}
	
	@Transactional
	@Override
	public void remover(Long id) {
		Cuisine cuisine = buscar(id);
		
		if (cuisine == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		entityManager.remove(cuisine);
	}

}
