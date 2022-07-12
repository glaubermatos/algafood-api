package com.algaworks.glauber.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum PurchaseStatus {

	CREATED("Criado"),
	CONFIRMED("Confirmado", CREATED),
	DELIVERED("Entregue", CONFIRMED),
	CANCELED("Cancelado", CREATED);
	
	private String description;
	private List<PurchaseStatus> previusStatus;
	
	private PurchaseStatus(String description, PurchaseStatus... previusStatus) {
		this.description = description;
		this.previusStatus = Arrays.asList(previusStatus);
	}

	public String getDescription() {
		return description;
	}
	
	public boolean cannotChangeTo(PurchaseStatus newPurchaseStatus) {
		return !newPurchaseStatus.previusStatus.contains(this);
	}
	
}
 