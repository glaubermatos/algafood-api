package com.algaworks.glauber.algafood.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.PermissionModelAssembler;
import com.algaworks.glauber.algafood.api.model.PermissionModel;
import com.algaworks.glauber.algafood.domain.model.Permission;
import com.algaworks.glauber.algafood.domain.model.UserGroup;
import com.algaworks.glauber.algafood.domain.service.UserGroupRegistrationService;

@RestController
@RequestMapping("/groups/{userGroupId}/permissions")
public class UserGroupPermissionController {
	
	@Autowired
	private PermissionModelAssembler permissionModelAssembler;
	
	@Autowired
	private UserGroupRegistrationService userGroupRegistrationService;
	
	@GetMapping
	public List<PermissionModel> index(@PathVariable Long userGroupId) {
		UserGroup userGroup = userGroupRegistrationService.findUserGroupByIdOrElseThrow(userGroupId);
		Set<Permission> groupPermissions = userGroup.getPermissions();
		
		return permissionModelAssembler.toCollectionModel(groupPermissions);
	}
	
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associatePermission(@PathVariable Long userGroupId, @PathVariable Long permissionId) {
		userGroupRegistrationService.associate(userGroupId, permissionId);
	}
	
	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociatePermission(@PathVariable Long userGroupId, @PathVariable Long permissionId) {
		userGroupRegistrationService.disassociate(userGroupId, permissionId);
	}
}
