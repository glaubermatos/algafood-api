package com.algaworks.glauber.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

}
