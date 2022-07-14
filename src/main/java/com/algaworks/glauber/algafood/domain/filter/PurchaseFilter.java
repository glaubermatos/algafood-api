package com.algaworks.glauber.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class PurchaseFilter {

	private Long clientId;
	private Long restaurantId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime createdAtInitial;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime createdAtFinal;
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public OffsetDateTime getCreatedAtInitial() {
		return createdAtInitial;
	}
	public void setCreatedAtInitial(OffsetDateTime createdAtInitial) {
		this.createdAtInitial = createdAtInitial;
	}
	public OffsetDateTime getCreatedAtFinal() {
		return createdAtFinal;
	}
	public void setCreatedAtFinal(OffsetDateTime createdAtFinal) {
		this.createdAtFinal = createdAtFinal;
	}
	
	
}
