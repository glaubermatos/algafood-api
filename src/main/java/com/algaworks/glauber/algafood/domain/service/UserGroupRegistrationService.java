package com.algaworks.glauber.algafood.domain.service;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_IN_USE;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.UserGroupNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Permission;
import com.algaworks.glauber.algafood.domain.model.UserGroup;
import com.algaworks.glauber.algafood.domain.repository.UserGroupRepository;

@Service
public class UserGroupRegistrationService {

	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private PermissionRegistrationService permissionRegistrationService;
	
	@Transactional
	public UserGroup save(UserGroup userGroup) {
		return userGroupRepository.save(userGroup);
	}

	@Transactional
	public void delete(Long groupId) {
		try {
			userGroupRepository.deleteById(groupId);
			userGroupRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new UserGroupNotFoundException(groupId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, "Grupo", groupId));
		}
	}
	
	@Transactional
	public void associate(Long userGroupId, Long permissionId) {
		UserGroup userGroup = findUserGroupByIdOrElseThrow(userGroupId);
		Permission permission = permissionRegistrationService.findPermissionByIdOrElseThrow(permissionId);
		
		userGroup.associatePermission(permission);
	}
	
	@Transactional
	public void disassociate(Long userGroupId, Long permissionId) {
		UserGroup userGroup = findUserGroupByIdOrElseThrow(userGroupId);
		Permission permission = permissionRegistrationService.findPermissionByIdOrElseThrow(permissionId);
		
		userGroup.disassociatePermission(permission);
	}
	
	public UserGroup findUserGroupByIdOrElseThrow(Long id) {
		try {
			return userGroupRepository.findByIdOrElseThrow(id);
		} catch (EntityNotFoundException e) {
			throw new UserGroupNotFoundException(id);
		}
	}
}
