package com.algaworks.glauber.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.model.Purchase;

@Service
public class PurchaseStatusFlowService {
	
	@Autowired
	private PurchaseRegistrationService perPurchaseRegistrationService;

	@Transactional
	public void confirmation(String purchaseCode) {
		Purchase purchase = perPurchaseRegistrationService.findPurchaseByCodeOrElseThrow(purchaseCode);
		purchase.confirm();
	}

	@Transactional
	public void deliver(String purchaseCode) {
		Purchase purchase = perPurchaseRegistrationService.findPurchaseByCodeOrElseThrow(purchaseCode);
		purchase.deliver();
	}

	@Transactional
	public void cancel(String purchaseCode) {
		Purchase purchase = perPurchaseRegistrationService.findPurchaseByCodeOrElseThrow(purchaseCode);
		
//		if (!purchase.getStatus().equals(PurchaseStatus.CREATED)) {
//			throw new BusinessException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
//					purchase.getId(),
//					purchase.getStatus().getDescription(),
//					PurchaseStatus.CANCELED.getDescription()));
//		}
		
		purchase.cancel();
	}
}
