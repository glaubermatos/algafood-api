package com.algaworks.glauber.algafood.api.model;

import com.algaworks.glauber.algafood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;

public class CuisineModel {
	
	@JsonView(RestaurantView.Summary.class)
	private Long id;
	
	@JsonView(RestaurantView.Summary.class)
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
