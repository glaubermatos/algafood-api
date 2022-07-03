package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.StateNotFoundException;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.repository.CityRepository;
import com.algaworks.glauber.algafood.domain.service.CityRegistrationService;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CityRegistrationService cityRegistrationService;

	@GetMapping
	public List<City> listar() {
		return cityRepository.findAll();
	}
	
	@GetMapping("/{cityId}")
	public City buscar(@PathVariable Long cityId) {
		return cityRegistrationService.findCityByIdOrElseThrow(cityId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public City criar(@RequestBody @Valid City city) {
		try {
			return cityRegistrationService.salvar(city);			
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{cityId}")
	public City atualizar(@PathVariable Long cityId, @RequestBody @Valid City city) {
		City currentCity = cityRegistrationService.findCityByIdOrElseThrow(cityId);
		
		BeanUtils.copyProperties(city, currentCity, "id");
		
		try {
			return cityRegistrationService.salvar(currentCity);			
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cityId) {
		cityRegistrationService.deletar(cityId);
	}	
}
