package com.algaworks.glauber.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.ProductPhoto;
import com.algaworks.glauber.algafood.domain.repository.ProductRepositoryQueries;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public ProductPhoto save(ProductPhoto photo) {
		return entityManager.merge(photo);
	}

	@Transactional
	@Override
	public void delete(ProductPhoto photo) {
		entityManager.remove(photo);
	}

}
