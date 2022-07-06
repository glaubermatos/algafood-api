package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.input.CuisineInput;
import com.algaworks.glauber.algafood.domain.model.Cuisine;

@Component
public class CuisineInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cuisine toDomaiObject(CuisineInput cuisineInput) {
		return modelMapper.map(cuisineInput, Cuisine.class);
	}
	
	public void copyToDomainObject(CuisineInput cuisineInput, Cuisine cuisine) {
		modelMapper.map(cuisineInput, cuisine);
	}
}
