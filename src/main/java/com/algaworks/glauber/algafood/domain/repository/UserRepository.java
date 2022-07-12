package com.algaworks.glauber.algafood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {

	Optional<User> findByEmailIgnoreCase(String email);
}
