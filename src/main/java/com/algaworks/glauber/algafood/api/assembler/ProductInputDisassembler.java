package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.input.ProductInput;
import com.algaworks.glauber.algafood.domain.model.Product;

@Component
public class ProductInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Product toDomainObject(ProductInput productInput) {
		return modelMapper.map(productInput, Product.class);
	}
	
	public void copyToDomainObject(ProductInput productInput, Product product) {
		modelMapper.map(productInput, product);
	}
}
