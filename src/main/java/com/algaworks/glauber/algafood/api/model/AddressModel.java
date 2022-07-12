package com.algaworks.glauber.algafood.api.model;

public class AddressModel {
	
	private String postalCode;
	private String street;
	private String number;
	private String complement;
	private String district;
//	private CityModel city;
	private CitySummaryModel city;
	
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
	public CitySummaryModel getCity() {
		return city;
	}
	public void setCity(CitySummaryModel city) {
		this.city = city;
	}
	
	
}
