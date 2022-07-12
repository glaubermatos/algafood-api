package com.algaworks.glauber.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.UserGroupModel;
import com.algaworks.glauber.algafood.domain.model.UserGroup;

@Component
public class UserGroupModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserGroupModel toModel(UserGroup group) {
		return modelMapper.map(group, UserGroupModel.class);
	}
	
	public List<UserGroupModel> toCollectionModel(Collection<UserGroup> groups) {
		return groups.stream()
				.map(group -> toModel(group))
				.collect(Collectors.toList());
	}
}
