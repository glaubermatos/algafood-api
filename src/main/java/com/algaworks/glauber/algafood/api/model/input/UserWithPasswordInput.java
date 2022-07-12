package com.algaworks.glauber.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

public class UserWithPasswordInput extends UserInput {

	@NotBlank
	private String password;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
