package com.algaworks.glauber.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.glauber.algafood.api.model.AddressModel;
import com.algaworks.glauber.algafood.api.model.input.PurchaseItemInput;
import com.algaworks.glauber.algafood.domain.model.Address;
import com.algaworks.glauber.algafood.domain.model.PurchaseItem;

@Configuration
public class ModelmapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		var addresssToAddressModelTypeMap = modelMapper.createTypeMap(Address.class, AddressModel.class);
		addresssToAddressModelTypeMap.<String>addMapping(
				src -> src.getCity().getState().getName(), 
				(dest, value) -> dest.getCity().setState(value));
		
		var purchaseItemInputToPurchaseItemTypeMap = modelMapper.createTypeMap(PurchaseItemInput.class, PurchaseItem.class);
		purchaseItemInputToPurchaseItemTypeMap.addMappings(mapper -> mapper.skip(PurchaseItem::setId));
		
		return modelMapper;
	}
}
