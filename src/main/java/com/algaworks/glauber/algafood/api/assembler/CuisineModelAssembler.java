package com.algaworks.glauber.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.CuisineModel;
import com.algaworks.glauber.algafood.domain.model.Cuisine;

@Component
public class CuisineModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CuisineModel toModel(Cuisine cuisine) {
		return modelMapper.map(cuisine, CuisineModel.class);
	}
	
	public List<CuisineModel> toCollectionModel(List<Cuisine> cuisines) {
		return cuisines.stream()
				.map(cuisine -> toModel(cuisine))
				.collect(Collectors.toList());
	}
}
