package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.ProductPhotoModel;
import com.algaworks.glauber.algafood.domain.model.ProductPhoto;

@Component
public class ProductPhotoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProductPhotoModel toModel(ProductPhoto photo) {
		return modelMapper.map(photo, ProductPhotoModel.class);
	}
}
