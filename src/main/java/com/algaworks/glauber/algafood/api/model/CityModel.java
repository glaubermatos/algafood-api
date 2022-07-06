package com.algaworks.glauber.algafood.api.model;

public class CityModel {

	private Long id;	
	private String name;
	private StateModel state;
	
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
	public StateModel getState() {
		return state;
	}
	public void setState(StateModel state) {
		this.state = state;
	}
	
}
