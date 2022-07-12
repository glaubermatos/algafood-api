package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.UserGroupModelAssembler;
import com.algaworks.glauber.algafood.api.model.UserGroupModel;
import com.algaworks.glauber.algafood.domain.model.User;
import com.algaworks.glauber.algafood.domain.service.UserRegistrationService;

@RestController
@RequestMapping("/users/{userId}/groups")
public class UserUserGroupController {
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@Autowired
	private UserGroupModelAssembler userGroupModelAssembler;
	
	@GetMapping
	public List<UserGroupModel> index(@PathVariable Long userId) {
		User user = userRegistrationService.findUserByIdOrElseThrow(userId);
		
		return userGroupModelAssembler.toCollectionModel(user.getGroups());
	}
	
	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associateGroup(@PathVariable Long userId, @PathVariable Long groupId) {
		userRegistrationService.associateGroup(userId, groupId);
	}
	
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociateGroup(@PathVariable Long userId, @PathVariable Long groupId) {
		userRegistrationService.disassociateGroup(userId, groupId);
	}

}
