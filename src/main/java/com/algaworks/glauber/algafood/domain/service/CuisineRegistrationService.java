package com.algaworks.glauber.algafood.domain.service;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_IN_USE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.CuisineNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;

@Service
public class CuisineRegistrationService {
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	public Cuisine salvar(Cuisine cuisine) {
		return cuisineRepository.save(cuisine);
	}
	
	public void excluir(Long cuisineId) {
		try {
			cuisineRepository.deleteById(cuisineId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new CuisineNotFoundException(cuisineId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, "Cozinha", cuisineId));
		} 
	}
	
	/*
	 * Sem o uso de exceptions de negócio passa para o controller a responsabilidade
	 * de tratar as exceptions EmptyResultDataAccessException e
	 * DataIntegrityViolationException
	 */
//	public void excluir(Long cuisineId) throws EmptyResultDataAccessException, DataIntegrityViolationException {
//		cuisineRepository.deleteById(cuisineId);
//	}
	
	public Cuisine findCuisineByIdOrElseThrow(Long id) {
		try {
			return cuisineRepository.findByIdOrElseThrow(id);
		} catch (EntityNotFoundException e) {
			throw new CuisineNotFoundException(id);
		}
	}

}
