package com.algaworks.glauber.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.PermissionNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Permission;
import com.algaworks.glauber.algafood.domain.repository.PermissionRepository;

@Service
public class PermissionRegistrationService {

	@Autowired
	private PermissionRepository permissionRepository;
	
	public Permission findPermissionByIdOrElseThrow(Long permissionId) {
		try {
			return permissionRepository.findByIdOrElseThrow(permissionId);
		} catch (EntityNotFoundException e) {
			throw new PermissionNotFoundException(permissionId);
		}
	}
}
