package com.algaworks.glauber.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.State;

@Repository
public interface StateRepository extends CustomJpaRepository<State, Long>{

}
