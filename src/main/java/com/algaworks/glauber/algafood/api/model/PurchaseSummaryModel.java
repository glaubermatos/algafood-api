package com.algaworks.glauber.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//JsonFilter utilizado junto com a classe MappingJacksonValue para filtrar os campos retornados para o client
//@JsonFilter("purchaseFilter")//removida para uso do filtro com specification
public class PurchaseSummaryModel {

	private String code;
	private BigDecimal subtotal;
	private BigDecimal freightRate;
	private BigDecimal totalAmount;
	private String status;
	private OffsetDateTime createdAt;
	private RestaurantSummaryModel restaurant;
	private UserModel client;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getFreightRate() {
		return freightRate;
	}
	public void setFreightRate(BigDecimal freightRate) {
		this.freightRate = freightRate;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public RestaurantSummaryModel getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(RestaurantSummaryModel restaurant) {
		this.restaurant = restaurant;
	}
	public UserModel getClient() {
		return client;
	}
	public void setClient(UserModel client) {
		this.client = client;
	}
	
}
