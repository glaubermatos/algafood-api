package com.algaworks.glauber.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.Permission;

@Repository
public interface PermissionRepository extends CustomJpaRepository<Permission, Long>{

}
