package com.algaworks.glauber.algafood.domain.exception;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_NOT_FOUND;

public class ProductNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
		super(message);
	}
	
	public ProductNotFoundException(Long id) {
		this(String.format(MSG_ENTITY_NOT_FOUND, "Produto", id));
	}

}
