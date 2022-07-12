package com.algaworks.glauber.algafood.domain.exception;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_NOT_FOUND;

public class UserNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(Long id) {
		this(String.format(MSG_ENTITY_NOT_FOUND, "Usuário", id));
	}

}
