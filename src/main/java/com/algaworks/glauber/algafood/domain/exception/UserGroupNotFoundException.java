package com.algaworks.glauber.algafood.domain.exception;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_NOT_FOUND;

public class UserGroupNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public UserGroupNotFoundException(String message) {
		super(message);
	}
	
	public UserGroupNotFoundException(Long id) {
		this(String.format(MSG_ENTITY_NOT_FOUND, "Grupo", id));
	}

}
