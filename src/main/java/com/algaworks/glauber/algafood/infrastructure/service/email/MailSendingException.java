package com.algaworks.glauber.algafood.infrastructure.service.email;

public class MailSendingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MailSendingException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailSendingException(String message) {
		super(message);
	}

	
}
