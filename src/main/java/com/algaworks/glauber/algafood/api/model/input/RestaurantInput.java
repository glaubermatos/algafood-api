package com.algaworks.glauber.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class RestaurantInput {

	@NotBlank
	private String name;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal freightRate;
	
	@Valid
	@NotNull
	private CuisineInputId cuisine;
	
	@Valid
	@NotNull
	private AddressInput address;
	
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
	public CuisineInputId getCuisine() {
		return cuisine;
	}
	public void setCuisine(CuisineInputId cuisine) {
		this.cuisine = cuisine;
	}
	public AddressInput getAddress() {
		return address;
	}
	public void setAddress(AddressInput address) {
		this.address = address;
	}
	
}
