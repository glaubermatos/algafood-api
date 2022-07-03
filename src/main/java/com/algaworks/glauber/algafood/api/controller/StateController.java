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
	public State buscar(@PathVariable Long stateId) {
		return stateRegistrationService.findStateByIdOrElseThrow(stateId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State criar(@RequestBody @Valid State state) {
		return stateRegistrationService.salvar(state);
	}
	
	@PutMapping("/{stateId}")
	public State atualizar(@PathVariable Long stateId, @RequestBody @Valid State state) {
		State currentState = stateRegistrationService.findStateByIdOrElseThrow(stateId);
		
		BeanUtils.copyProperties(state, currentState, "id");
		
		return stateRegistrationService.salvar(currentState);
	}
	
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long stateId) {
		stateRegistrationService.deletar(stateId);
	}

}
