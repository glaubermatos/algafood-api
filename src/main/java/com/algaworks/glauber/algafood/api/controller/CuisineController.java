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
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;
import com.algaworks.glauber.algafood.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping("/cuisines")
public class CuisineController {
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;
	

	@GetMapping
	public List<Cuisine> listar() {
		return cuisineRepository.findAll();
	}
	
	@GetMapping("/{cuisineId}")
	public ResponseEntity<Cuisine> buscar(@PathVariable Long cuisineId) {
		Optional<Cuisine> cozinhaOptional = cuisineRepository.findById(cuisineId);
		
		if (cozinhaOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(cozinhaOptional.get());	
	}
	
	@PostMapping
	public ResponseEntity<Cuisine> criar(@RequestBody Cuisine cozinha) {
		cozinha = cuisineRegistrationService.salvar(cozinha);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
	}
	
	@PutMapping("/{cuizineId}")
	public ResponseEntity<Cuisine> atualizar(@PathVariable Long cuizineId, @RequestBody Cuisine cozinha) {
		Optional<Cuisine> currentCuisineOptional = cuisineRepository.findById(cuizineId);
		
		if (currentCuisineOptional.isPresent()) {
			BeanUtils.copyProperties(cozinha, currentCuisineOptional.get(), "id");
			
			Cuisine cuisine = cuisineRegistrationService.salvar(currentCuisineOptional.get());
			
			return ResponseEntity.ok(cuisine);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{cuisineId}")
	public ResponseEntity<?> remover(@PathVariable Long cuisineId) {		
		try {
			cuisineRegistrationService.excluir(cuisineId);
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			
		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
