package com.algaworks.glauber.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.glauber.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	@NotNull
	@PositiveOrZero
	@Column(name = "freight_rate", nullable = false)//opcional o hibernate ja cria nesse padrão
	private BigDecimal freightRate;
	
	@JsonIgnoreProperties(value = "name", allowGetters = true)
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CuisineId.class)
	@NotNull
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)//se for usar como lazy precisa adicionar o @IgnoreProperties para hibernateLazyInitializer
	@JoinColumn(name = "cuisine_id", nullable = false)//padrão do JPA
	private Cuisine cuisine;
	
	@Column(nullable = false)
	private Boolean active = Boolean.TRUE;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurant_payment_method", 
			joinColumns = @JoinColumn(name = "restaurant_id"), 
			inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private Set<PaymentMethod> paymentMethods = new HashSet<>();
	
	@JsonIgnore
	@Embedded
	private Address address;
	
//	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime createdAt;
	
//	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updatedAt;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
	
	private Boolean open = Boolean.FALSE;
	
	@ManyToMany
	@JoinTable(name = "restaurant_user_responsible",
		joinColumns = @JoinColumn(name = "restaurant_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> responsibles = new HashSet<>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Cuisine getCuisine() {
		return cuisine;
	}
	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}
	public Set<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Set<User> getResponsibles() {
		return responsibles;
	}
	public void setResponsibles(Set<User> responsibles) {
		this.responsibles = responsibles;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		return Objects.equals(id, other.id);
	}
	
	//TODO: traduzir nome do metodo
	public boolean associarComFormaPagamento(PaymentMethod paymentMethod) {
		return this.getPaymentMethods().add(paymentMethod);
	}
	
	//TODO: traduzir nome do metodo
	public boolean desassociarComFormaPagamento(PaymentMethod paymentMethod) {
		return this.getPaymentMethods().remove(paymentMethod);
	}
	
	public void close() {
		setOpen(Boolean.FALSE);
	}
	
	public void open() {
		setOpen(Boolean.TRUE);
	}

	public void activate() {
		setActive(Boolean.TRUE);
	}

	public void inactivate() {
		setActive(Boolean.FALSE);
	}
	
	public Boolean addResponsible(User userResponsible) {
		return getResponsibles().add(userResponsible);
	}
	
	public Boolean removeResponsible(User userResponsible) {
		return getResponsibles().remove(userResponsible);
	}
	public Boolean notAcceptPaymentMethod(PaymentMethod paymentMethod) {
		return !getPaymentMethods().contains(paymentMethod);
	}
	
}
