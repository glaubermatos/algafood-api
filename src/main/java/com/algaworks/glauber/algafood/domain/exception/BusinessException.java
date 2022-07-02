package com.algaworks.glauber.algafood.domain.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

}
