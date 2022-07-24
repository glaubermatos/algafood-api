package com.algaworks.glauber.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.model.Purchase;
import com.algaworks.glauber.algafood.domain.repository.PurchaseRepository;

@Service
public class PurchaseStatusFlowService {
	
	@Autowired
	private PurchaseRegistrationService perPurchaseRegistrationService;
	
	@Autowired
	private PurchaseRepository purchaseRepository;

	@Transactional
	public void confirmation(String purchaseCode) {
		Purchase purchase = perPurchaseRegistrationService.findPurchaseByCodeOrElseThrow(purchaseCode);
		purchase.confirm();
		
		purchaseRepository.save(purchase);
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
		
		purchaseRepository.save(purchase);//não precisa chamar o método save, necessário apenas devido ao eventos
	}
}
