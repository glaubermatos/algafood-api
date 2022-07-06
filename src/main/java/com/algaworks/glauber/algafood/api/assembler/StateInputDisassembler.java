package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.input.StateInput;
import com.algaworks.glauber.algafood.domain.model.State;

@Component
public class StateInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public State toDomainObject(StateInput stateINput) {
		return modelMapper.map(stateINput, State.class);
	}
	
	public void copyToDomainObject(StateInput stateInput, State state) {
		modelMapper.map(stateInput, state);
	}
}
