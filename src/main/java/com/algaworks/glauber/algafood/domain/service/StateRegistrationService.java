package com.algaworks.glauber.algafood.domain.service;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.StateNotFoundException;
import com.algaworks.glauber.algafood.domain.model.State;
import com.algaworks.glauber.algafood.domain.repository.StateRepository;

@Service
public class StateRegistrationService {
	
	@Autowired
	private StateRepository stateRepository;

	@Transactional
	public State salvar(State state) {
		return stateRepository.save(state);
	}
	
	//com a anotação @Transactional lança a exceção abaixo e não relança como EntityInUseException
	//org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement
	@Transactional
	public void deletar(Long stateId) {
		try {
			stateRepository.deleteById(stateId);
			stateRepository.flush();//descarrrega todas as operações pendentes de alterações de dados nexecutando no banco de dados, resolve o problema da não captura da exceção citada acima
			
		} catch (EmptyResultDataAccessException e) {
			throw new StateNotFoundException(stateId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, "Estado", stateId));
		}
	}
	
	public State findStateByIdOrElseThrow(Long stateId) {
		try {
			return stateRepository.findByIdOrElseThrow(stateId);
			
		} catch (EntityNotFoundException e) {
			throw new StateNotFoundException(stateId);
		}
	}
}
