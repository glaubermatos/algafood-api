package com.algaworks.glauber.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CityInput {
	
	@NotBlank
	private String name;
	
	@Valid
	@NotNull
	private StateInputId state;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public StateInputId getState() {
		return state;
	}
	public void setState(StateInputId state) {
		this.state = state;
	}

}
