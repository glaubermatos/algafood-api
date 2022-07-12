package com.algaworks.glauber.algafood.api.model.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.domain.model.Purchase;

@Component
public class PurchaseInpurDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Purchase toDomainObject(PurchaseInput purchaseInput) {
		return modelMapper.map(purchaseInput, Purchase.class);
	}
	
	public void copyToDomainObject(PurchaseInput purchaseInput, Purchase purchase) {
		modelMapper.map(purchaseInput, purchase);
	}
}
