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
		return cuisineRepository.listar();
	}
	
	@GetMapping("/{cuisineId}")
	public ResponseEntity<Cuisine> buscar(@PathVariable Long cuisineId) {
		Cuisine cozinha = cuisineRepository.buscar(cuisineId);
		
		if (cozinha == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(cozinha);	
	}
	
	@PostMapping
	public ResponseEntity<Cuisine> criar(@RequestBody Cuisine cozinha) {
		cozinha = cuisineRegistrationService.salvar(cozinha);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
	}
	
	@PutMapping("/{cuizineId}")
	public ResponseEntity<Cuisine> atualizar(@PathVariable Long cuizineId, @RequestBody Cuisine cozinha) {
		Cuisine currentCuisine = cuisineRepository.buscar(cuizineId);
		
		if (currentCuisine != null) {
			BeanUtils.copyProperties(cozinha, currentCuisine, "id");
			
			currentCuisine = cuisineRegistrationService.salvar(currentCuisine);
			
			return ResponseEntity.ok(currentCuisine);
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
