package com.algaworks.glauber.algafood.domain.exception;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_EMAIL_IN_USE;

public class UserAlreadyExistsForEmailInformed extends BusinessException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsForEmailInformed(String email) {
		super(String.format(MSG_EMAIL_IN_USE, "Usuário", email));
	}

}
