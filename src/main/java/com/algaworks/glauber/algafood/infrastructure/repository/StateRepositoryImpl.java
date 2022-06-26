package com.algaworks.glauber.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.domain.model.State;
import com.algaworks.glauber.algafood.domain.repository.StateRepository;

@Component
public class StateRepositoryImpl implements StateRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<State> listar() {
		return entityManager.createQuery("from State", State.class)
				.getResultList();
	}

	@Override
	public State buscar(Long id) {
		return entityManager.find(State.class, id);
	}

	@Transactional
	@Override
	public State salvar(State state) {
		return entityManager.merge(state);
	}

	@Transactional
	@Override
	public void remover(Long stateId) {
		State state = buscar(stateId);
		
		if (state == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		entityManager.remove(state);
	}

}
