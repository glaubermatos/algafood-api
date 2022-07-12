package com.algaworks.glauber.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.UserGroup;

@Repository
public interface UserGroupRepository extends CustomJpaRepository<UserGroup, Long> {

}
