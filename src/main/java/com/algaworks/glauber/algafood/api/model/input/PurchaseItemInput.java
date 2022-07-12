package com.algaworks.glauber.algafood.api.model.input;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class PurchaseItemInput {

	@NotNull
	private Long productId;
	
	@NotNull
	@PositiveOrZero
	private Integer quantity;

	private String note;
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseItemInput other = (PurchaseItemInput) obj;
		return Objects.equals(productId, other.productId);
	}
	
}
