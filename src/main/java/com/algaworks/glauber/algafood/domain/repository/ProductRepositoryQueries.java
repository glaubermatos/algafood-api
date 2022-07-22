package com.algaworks.glauber.algafood.domain.repository;

import com.algaworks.glauber.algafood.domain.model.ProductPhoto;

public interface ProductRepositoryQueries {

	ProductPhoto save(ProductPhoto photo);
	
	void delete(ProductPhoto photo);
}
