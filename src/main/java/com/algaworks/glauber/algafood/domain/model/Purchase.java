package com.algaworks.glauber.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.glauber.algafood.domain.exception.BusinessException;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String code;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column(nullable = false)
	private BigDecimal freightRate;

	@Column(nullable = false)
	private BigDecimal totalAmount;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime createdAt;

	@Column(columnDefinition = "datetime")
	private OffsetDateTime confirmationDate;

	@Column(columnDefinition = "datetime")
	private OffsetDateTime cancellationDate;

	@Column(columnDefinition = "datetime")
	private OffsetDateTime deliveryDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PurchaseStatus status = PurchaseStatus.CREATED;
	
	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
	private List<PurchaseItem> items = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "user_client_id", nullable = false)
	private User client;
	
	@Embedded
	private Address address;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private PaymentMethod paymentMethod;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public PurchaseStatus getStatus() {
		return status;
	}
//	public void setStatus(PurchaseStatus status) {
//		this.status = status;
//	}
	public List<PurchaseItem> getItems() {
		return items;
	}
	public void setItems(List<PurchaseItem> items) {
		this.items = items;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
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
		Purchase other = (Purchase) obj;
		return Objects.equals(id, other.id);
	}

	public void calculatePuchase() {
		BigDecimal freightRate = this.getRestaurant().getFreightRate();
		
		getItems().forEach(PurchaseItem::calculateTotalPrice);
		
		setSubtotal(getItems().stream()
				.map(item -> item.getTotalPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		
//		setSubtotal(getItems().stream()
//				.map(PurchaseItem::getTotalPrice)
//				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
		
		setTotalAmount(getSubtotal().add(freightRate));
	}
	
	public void confirm() {
		setStatus(PurchaseStatus.CONFIRMED);
		setConfirmationDate(OffsetDateTime.now());
	}
	
	public void deliver() {
		setStatus(PurchaseStatus.DELIVERED);
		setDeliveryDate(OffsetDateTime.now());
	}
	
	public void cancel() {
		setStatus(PurchaseStatus.CANCELED);
		setCancellationDate(OffsetDateTime.now());
	}
	
	private void setStatus(PurchaseStatus newStatus) {
		if (getStatus().cannotChangeTo(newStatus)) {
			throw new BusinessException(String.format("Status do pedido de código %s não pode ser alterado de %s para %s",
					getCode(),
					getStatus().getDescription(),
					newStatus.getDescription()));
		}
		
		this.status = newStatus;
	}
	
	@PrePersist
	private void generateCode() {
		setCode(UUID.randomUUID().toString());
	}

}
