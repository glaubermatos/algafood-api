package com.algaworks.glauber.algafood.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PurchaseItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer quantity; 

	@Column(nullable = false)
	private BigDecimal unitPrice;

	@Column(nullable = false)
	private BigDecimal totalPrice;
	
	private String note;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Purchase purchase;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Product product;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseItem other = (PurchaseItem) obj;
		return Objects.equals(id, other.id);
	}
	
	public void calculateTotalPrice() {
		BigDecimal unitPrice = this.getUnitPrice();
		Integer quantity = this.getQuantity();
		
		if (unitPrice == null) {
			unitPrice = BigDecimal.ZERO;
		}
		
		if (quantity == null) {
			quantity = 0;
		}
		
		setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)) );
	}
}
