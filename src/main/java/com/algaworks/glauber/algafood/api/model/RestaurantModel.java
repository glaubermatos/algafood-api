package com.algaworks.glauber.algafood.api.model;

import java.math.BigDecimal;

public class RestaurantModel {
	
	private Long id;
	private String name;
	private BigDecimal freightRate;
	private CuisineModel cuisine;
	private Boolean active;
	private Boolean open;
	private AddressModel address;
	
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
	public BigDecimal getFreightRate() {
		return freightRate;
	}
	public void setFreightRate(BigDecimal freightRate) {
		this.freightRate = freightRate;
	}
	public CuisineModel getCuisine() {
		return cuisine;
	}
	public void setCuisine(CuisineModel cuisine) {
		this.cuisine = cuisine;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public AddressModel getAddress() {
		return address;
	}
	public void setAddress(AddressModel address) {
		this.address = address;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}

}
