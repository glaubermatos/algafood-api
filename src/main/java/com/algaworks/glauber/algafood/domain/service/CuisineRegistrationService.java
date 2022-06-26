package com.algaworks.glauber.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;

@Service
public class CuisineRegistrationService {
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	public Cuisine salvar(Cuisine cuisine) {
		return cuisineRepository.salvar(cuisine);
	}
	
	public void excluir(Long cuisineId) {
		try {
			cuisineRepository.remover(cuisineId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format("Cozinha de código %d não existe", cuisineId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format("Cozinha de código %d não pode ser removida, pois está em uso", cuisineId));
		} 
	}

}
