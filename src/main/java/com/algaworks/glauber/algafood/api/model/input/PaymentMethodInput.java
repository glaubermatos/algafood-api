package com.algaworks.glauber.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

public class PaymentMethodInput {
	
	@NotBlank
	private String description;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
