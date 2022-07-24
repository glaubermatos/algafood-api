package com.algaworks.glauber.algafood.domain.event;

import com.algaworks.glauber.algafood.domain.model.Purchase;

public class CanceledPurchaseEvent {

	private Purchase purchase;
	
	public CanceledPurchaseEvent(Purchase purchase) {
		this.purchase = purchase;
	}
	
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
}
