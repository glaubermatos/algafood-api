package com.algaworks.glauber.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.model.State;
import com.algaworks.glauber.algafood.domain.repository.CityRepository;
import com.algaworks.glauber.algafood.domain.repository.StateRepository;

@Service
public class CityRegistrationService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;

	public City salvar(City city) {
		Long stateId = city.getState().getId();
		
		State state = stateRepository.buscar(stateId);
		
		if (state == null) {
			throw new EntityNotFoundException(String.format("Estado de código %d não existe", stateId));
		}
		
		city.setState(state);
		
		return cityRepository.salvar(city);
	}
	
	public void deletar(Long cityId) {
		try {
			cityRepository.remover(cityId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format("Cidade de código %d não existe", cityId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format("Cidade de código %d não pode ser removida, pois está em uso", cityId));
		}
	}
}
