package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.input.UserGroupInput;
import com.algaworks.glauber.algafood.domain.model.UserGroup;

@Component
public class UserGroupInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserGroup toDomainObject(UserGroupInput userGroupInput) {
		return modelMapper.map(userGroupInput, UserGroup.class);
	}
	
	public void copyToDomainObject(UserGroupInput userGroupInput, UserGroup userGroup) {
		modelMapper.map(userGroupInput, userGroup);
	}
}
