package com.algaworks.glauber.algafood.domain.exception;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_NOT_FOUND;

public class PaymentMethodNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PaymentMethodNotFoundException(String message) {
		super(message);
	}
	
	public PaymentMethodNotFoundException(Long id) {
		this(String.format(MSG_ENTITY_NOT_FOUND, "Método de pagamento", id));
	}

}
