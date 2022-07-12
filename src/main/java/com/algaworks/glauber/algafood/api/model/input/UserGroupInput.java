package com.algaworks.glauber.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

public class UserGroupInput {

	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
