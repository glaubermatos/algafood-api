package com.algaworks.glauber.algafood.domain.repository;

import java.util.List;

import com.algaworks.glauber.algafood.domain.model.Permission;

public interface PermissionRepository {

	List<Permission> listar();
	Permission buscar(Long id);
	Permission salvar(Permission permission);
	void remover(Permission permission);
}
