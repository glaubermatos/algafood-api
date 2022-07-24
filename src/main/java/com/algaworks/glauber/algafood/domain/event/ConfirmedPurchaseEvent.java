package com.algaworks.glauber.algafood.domain.event;

import com.algaworks.glauber.algafood.domain.model.Purchase;

public class ConfirmedPurchaseEvent {

	private Purchase purchase;

	public ConfirmedPurchaseEvent(Purchase purchase) {
		this.purchase = purchase;
	}
	
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
}
