package com.algaworks.glauber.algafood.domain.exception;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_NOT_FOUND;

public class CuisineNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CuisineNotFoundException(String message) {
		super(message);
	}
	
	public CuisineNotFoundException(Long id) {
		this(String.format(MSG_ENTITY_NOT_FOUND, "Cozinha", id));
	}

}
