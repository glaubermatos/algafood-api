package com.algaworks.glauber.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.PurchaseSummaryModel;
import com.algaworks.glauber.algafood.domain.model.Purchase;

@Component
public class PurchaseSummaryModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PurchaseSummaryModel toModel(Purchase purchase) {
		return modelMapper.map(purchase, PurchaseSummaryModel.class);
	}
	
	public List<PurchaseSummaryModel> toCollectionModel(Collection<Purchase> purchases) {
		return purchases.stream()
				.map(purchase -> toModel(purchase))
				.collect(Collectors.toList());
	}
}
