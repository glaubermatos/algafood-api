package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.glauber.algafood.api.assembler.StateInputDisassembler;
import com.algaworks.glauber.algafood.api.assembler.StateModelAssembler;
import com.algaworks.glauber.algafood.api.model.StateModel;
import com.algaworks.glauber.algafood.api.model.input.StateInput;
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
	
	@Autowired
	private StateModelAssembler stateModelAssembler;
	
	@Autowired
	private StateInputDisassembler stateInputDisassembler;
	
	@GetMapping
	public List<StateModel> listar() {
		return stateModelAssembler.toCollectionModel(stateRepository.findAll());
	}
	
	@GetMapping("/{stateId}")
	public StateModel buscar(@PathVariable Long stateId) {
		return stateModelAssembler.toModel(stateRegistrationService.findStateByIdOrElseThrow(stateId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateModel criar(@RequestBody @Valid StateInput stateInput) {
		return stateModelAssembler.toModel(stateRegistrationService.salvar(stateInputDisassembler.toDomainObject(stateInput)));
	}
	
	@PutMapping("/{stateId}")
	public StateModel atualizar(@PathVariable Long stateId, @RequestBody @Valid StateInput stateInput) {
		State currentState = stateRegistrationService.findStateByIdOrElseThrow(stateId);
		
		stateInputDisassembler.copyToDomainObject(stateInput, currentState);
//		BeanUtils.copyProperties(state, currentState, "id");
		
		return stateModelAssembler.toModel(stateRegistrationService.salvar(currentState));
	}
	
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long stateId) {
		stateRegistrationService.deletar(stateId);
	}

}
