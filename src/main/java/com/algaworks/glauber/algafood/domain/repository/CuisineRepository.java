package com.algaworks.glauber.algafood.domain.repository;

import java.util.List;

import com.algaworks.glauber.algafood.domain.model.Cuisine;

public interface CuisineRepository {
	
	List<Cuisine> listar();
	Cuisine buscar(Long id);
	Cuisine salvar(Cuisine cuisine);
	void remover(Cuisine cuisine);
	
}
