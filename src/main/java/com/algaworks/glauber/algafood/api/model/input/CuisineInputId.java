package com.algaworks.glauber.algafood.api.model.input;

import javax.validation.constraints.NotNull;

public class CuisineInputId {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
