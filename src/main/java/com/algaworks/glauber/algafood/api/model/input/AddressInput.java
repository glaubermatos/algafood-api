package com.algaworks.glauber.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddressInput {
	
	@NotBlank
	private String postalCode;

	@NotBlank
	private String street;

	@NotBlank
	private String number;

	private String complement;

	@NotBlank
	private String district;
	
	@Valid
	@NotNull
	private CityInputId city;

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public CityInputId getCity() {
		return city;
	}

	public void setCity(CityInputId city) {
		this.city = city;
	}
	
}
