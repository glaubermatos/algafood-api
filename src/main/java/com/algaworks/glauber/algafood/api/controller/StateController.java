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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.State;
import com.algaworks.glauber.algafood.domain.repository.StateRepository;
import com.algaworks.glauber.algafood.domain.service.StateRegistrationService;

@RestController
@RequestMapping("/states")
public class StateController {
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private StateRegistrationService stateRegistrationService;
	
	@GetMapping
	public List<State> listar() {
		return stateRepository.findAll();
	}
	
	@GetMapping("/{stateId}")
	public ResponseEntity<State> buscar(@PathVariable Long stateId) {
		Optional<State> stateOptional = stateRepository.findById(stateId);
		
		if (stateOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(stateOptional.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State criar(@RequestBody State state) {
		return stateRegistrationService.salvar(state);
	}
	
	@PutMapping("/{stateId}")
	public ResponseEntity<State> atualizar(@PathVariable Long stateId, @RequestBody State state) {
		Optional<State> currentStateOptional = stateRepository.findById(stateId);
		
		if (currentStateOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(state, currentStateOptional.get(), "id");
		
		state = stateRegistrationService.salvar(currentStateOptional.get());
		return ResponseEntity.ok(state);
	}
	
	@DeleteMapping("/{stateId}")
	public ResponseEntity<?> remover(@PathVariable Long stateId) {
		try {
			stateRegistrationService.deletar(stateId);
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
