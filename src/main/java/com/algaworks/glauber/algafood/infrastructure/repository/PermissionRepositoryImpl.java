package com.algaworks.glauber.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.domain.model.Permission;
import com.algaworks.glauber.algafood.domain.repository.PermissionRepository;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Permission> listar() {
		return entityManager.createQuery("from Permission", Permission.class)
				.getResultList();
	}

	@Override
	public Permission buscar(Long id) {
		return entityManager.find(Permission.class, id);
	}

	@Transactional
	@Override
	public Permission salvar(Permission permission) {
		return entityManager.merge(permission);
	}

	@Transactional
	@Override
	public void remover(Permission permission) {
		permission = buscar(permission.getId());
		entityManager.remove(permission);
	}

}
