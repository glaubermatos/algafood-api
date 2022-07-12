package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.input.PaymentMethodInput;
import com.algaworks.glauber.algafood.domain.model.PaymentMethod;

@Component
public class PaymentMethodInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PaymentMethod toDomainObject(PaymentMethodInput paymentMethodInput) {
		return modelMapper.map(paymentMethodInput, PaymentMethod.class);
	}
	
	public void copyToDomainObject(PaymentMethodInput paymentMethodInput, PaymentMethod paymentMethod) {
		modelMapper.map(paymentMethodInput, paymentMethod);
	}
}
