package com.algaworks.glauber.algafood.domain.service;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_IN_USE;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.CityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.model.State;
import com.algaworks.glauber.algafood.domain.repository.CityRepository;

@Service
public class CityRegistrationService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRegistrationService stateRegistrationService;

	@Transactional
	public City salvar(City city) {
		Long stateId = city.getState().getId();
		
		State state = stateRegistrationService.findStateByIdOrElseThrow(stateId);
		
		city.setState(state);
		
		return cityRepository.save(city);
	}
	
	@Transactional
	public void deletar(Long cityId) {
		try {
			cityRepository.deleteById(cityId);
			cityRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(cityId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, City.class.getSimpleName(), cityId));
		}
	}
	
	public City findCityByIdOrElseThrow(Long id) {
		try {
			return cityRepository.findByIdOrElseThrow(id);
		} catch (EntityNotFoundException e) {
			throw new CityNotFoundException(id);
		}
	}
}
