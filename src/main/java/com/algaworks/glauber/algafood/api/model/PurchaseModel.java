package com.algaworks.glauber.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PurchaseModel {

	private String code;
	private BigDecimal subtotal;
	private BigDecimal freightRate;
	private BigDecimal totalAmount;
	private String status;
	private OffsetDateTime createdAt;
	private OffsetDateTime confirmationDate;
	private OffsetDateTime cancellationDate;
	private OffsetDateTime deliveryDate;
	private RestaurantSummaryModel restaurant;
	private UserModel client;
	private PaymentMethodModel paymentMethod;
	private AddressModel address;
	private List<PurchaseItemModel> items;
	
	
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
	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public OffsetDateTime getConfirmationDate() {
		return confirmationDate;
	}
	public void setConfirmationDate(OffsetDateTime confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	public OffsetDateTime getCancellationDate() {
		return cancellationDate;
	}
	public void setCancellationDate(OffsetDateTime cancellationDate) {
		this.cancellationDate = cancellationDate;
	}
	public OffsetDateTime getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(OffsetDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<PurchaseItemModel> getItems() {
		return items;
	}
	public void setItems(List<PurchaseItemModel> items) {
		this.items = items;
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
	public AddressModel getAddress() {
		return address;
	}
	public void setAddress(AddressModel address) {
		this.address = address;
	}
	public PaymentMethodModel getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethodModel paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
