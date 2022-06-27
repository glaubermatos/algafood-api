package com.algaworks.glauber.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.State;
import com.algaworks.glauber.algafood.domain.repository.StateRepository;

@Service
public class StateRegistrationService {
	
	@Autowired
	private StateRepository stateRepository;

	public State salvar(State state) {
		return stateRepository.save(state);
	}
	
	public void deletar(Long stateId) {
		try {
			stateRepository.deleteById(stateId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format("Estado de código %d não existe", stateId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format("Estado de código %d não pode ser removido, pois está em uso", stateId));
		}
	}
}
