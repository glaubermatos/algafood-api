package com.algaworks.glauber.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
		return cityRepository.findAll();
	}
	
	@GetMapping("/{cityId}")
	public ResponseEntity<City> buscar(@PathVariable Long cityId) {
		Optional<City> cityOptional = cityRepository.findById(cityId);
		
		if (cityOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cityOptional.get());
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
		Optional<City> currentCityOptional = cityRepository.findById(cityId);
		
		if (currentCityOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(city, currentCityOptional.get(), "id");
		
		try {
			city = cityRegistrationService.salvar(currentCityOptional.get());
			return ResponseEntity.ok(city);
			
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
