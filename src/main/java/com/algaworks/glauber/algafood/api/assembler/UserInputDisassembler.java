package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.input.UserInput;
import com.algaworks.glauber.algafood.api.model.input.UserWithPasswordInput;
import com.algaworks.glauber.algafood.domain.model.User;

@Component
public class UserInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public User toDomainObject(UserWithPasswordInput userWithPasswordInput) {
		return modelMapper.map(userWithPasswordInput, User.class);
	}
	
	public void copyToDomainObject(UserInput userInput, User user) {
		modelMapper.map(userInput, user);
	}
}
