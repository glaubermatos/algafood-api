package com.algaworks.glauber.algafood.api.model.input;

import javax.validation.constraints.NotNull;

public class RestaurantIdInput {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
