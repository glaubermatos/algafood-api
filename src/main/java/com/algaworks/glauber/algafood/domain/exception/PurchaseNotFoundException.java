package com.algaworks.glauber.algafood.domain.exception;

public class PurchaseNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PurchaseNotFoundException(String purchaseCode) {
		super(String.format("Não existe um pedido com código %s", purchaseCode));
	}

}
