package com.algaworks.glauber.algafood.domain.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class ProductPhoto {
	
	@Id
	@Column(name = "product_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId //O JPA vai buscar o produto usando o id da entidade ProductPhoto que neste caso está mapeado para a coluna product_id
	private Product product;
	
	private String fileName;
	private String description;
	private String contentType;
	private Long size;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
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
		ProductPhoto other = (ProductPhoto) obj;
		return Objects.equals(id, other.id);
	}
	
	public Long getRestaurantId() {
		if (getProduct() != null) {
			return getProduct().getRestaurant().getId();
		}
		
		return null;
	}
}
