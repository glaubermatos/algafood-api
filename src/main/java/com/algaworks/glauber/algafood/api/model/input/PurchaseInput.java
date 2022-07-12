package com.algaworks.glauber.algafood.api.model.input;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PurchaseInput {

	@Valid
	@NotNull
	private RestaurantIdInput restaurant;
	
	@Valid
	@NotNull
	private AddressInput address;
	
	@Valid
	@NotNull
	private PaymentMethodIdInput paymentMethod;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private Set<PurchaseItemInput> items;
	
	public RestaurantIdInput getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(RestaurantIdInput restaurant) {
		this.restaurant = restaurant;
	}
	public AddressInput getAddress() {
		return address;
	}
	public void setAddress(AddressInput address) {
		this.address = address;
	}
	public PaymentMethodIdInput getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethodIdInput paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Set<PurchaseItemInput> getItems() {
		return items;
	}
	public void setItems(Set<PurchaseItemInput> items) {
		this.items = items;
	}
	
}
