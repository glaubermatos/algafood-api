package com.algaworks.glauber.algafood.domain.exception;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_NOT_FOUND;

public class StateNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public StateNotFoundException(String message) {
		super(message);
	}
	
	public StateNotFoundException(Long id) {
		this(String.format(MSG_ENTITY_NOT_FOUND, "Estado", id));
	}

}
