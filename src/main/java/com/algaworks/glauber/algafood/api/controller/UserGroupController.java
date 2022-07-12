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

import com.algaworks.glauber.algafood.api.assembler.UserGroupInputDisassembler;
import com.algaworks.glauber.algafood.api.assembler.UserGroupModelAssembler;
import com.algaworks.glauber.algafood.api.model.UserGroupModel;
import com.algaworks.glauber.algafood.api.model.input.UserGroupInput;
import com.algaworks.glauber.algafood.domain.model.UserGroup;
import com.algaworks.glauber.algafood.domain.repository.UserGroupRepository;
import com.algaworks.glauber.algafood.domain.service.UserGroupRegistrationService;

@RestController
@RequestMapping("/groups")
public class UserGroupController {

	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private UserGroupModelAssembler userGroupModelAssembler;
	
	@Autowired
	private UserGroupInputDisassembler userGroupInputDisassembler;
	
	@Autowired
	private UserGroupRegistrationService userGroupRegistrationService;
	
	@GetMapping
	public List<UserGroupModel> index() {
	 	return userGroupModelAssembler.toCollectionModel(userGroupRepository.findAll()); 
	}
	
	@GetMapping("/{groupId}")
	public UserGroupModel show(@PathVariable Long groupId) {
		return userGroupModelAssembler.toModel(userGroupRegistrationService.findUserGroupByIdOrElseThrow(groupId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserGroupModel create(@RequestBody @Valid UserGroupInput userGroupInput) {		
		return userGroupModelAssembler
				.toModel(userGroupRegistrationService
						.save(userGroupInputDisassembler
								.toDomainObject(userGroupInput)));
	}
	
	@PutMapping("/{groupId}")
	public UserGroupModel update(@PathVariable Long groupId, @RequestBody @Valid UserGroupInput userGroupInput) {
		UserGroup currencyUserGroup = userGroupRegistrationService.findUserGroupByIdOrElseThrow(groupId);
		
		userGroupInputDisassembler.copyToDomainObject(userGroupInput, currencyUserGroup);
		
		return userGroupModelAssembler.toModel(userGroupRegistrationService.save(currencyUserGroup));
	}

	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long groupId) {
		userGroupRegistrationService.delete(groupId);
	}
}
