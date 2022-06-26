package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
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
		return cityRepository.listar();
	}
	
	@GetMapping("/{cityId}")
	public ResponseEntity<City> buscar(@PathVariable Long cityId) {
		City city = cityRepository.buscar(cityId);
		
		if (city == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(city);
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@RequestBody City city) {
		try {
			city = cityRegistrationService.salvar(city);
			return ResponseEntity.status(HttpStatus.CREATED).body(city);
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{cityId}")
	public ResponseEntity<?> atualizar(@PathVariable Long cityId, @RequestBody City city) {
		City currentCity = cityRepository.buscar(cityId);
		
		if (currentCity == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(city, currentCity, "id");
		
		try {
			currentCity = cityRegistrationService.salvar(currentCity);
			return ResponseEntity.ok(currentCity);
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{cityId}")
	public ResponseEntity<?> remover(@PathVariable Long cityId) {
		try {
			cityRegistrationService.deletar(cityId);
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
