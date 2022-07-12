package com.algaworks.glauber.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.domain.service.PurchaseStatusFlowService;

@RestController
@RequestMapping("/purchases/{purchaseCode}")
public class PurchaseStatusFlowController {

	@Autowired
	private PurchaseStatusFlowService purchaseStatusFlowService;
	
	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmate(@PathVariable String purchaseCode) {
		purchaseStatusFlowService.confirmation(purchaseCode);
	}
	
	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliver(@PathVariable String purchaseCode) {
		purchaseStatusFlowService.deliver(purchaseCode); 
	}
	
	@PutMapping("/cancellation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable String purchaseCode) {
		purchaseStatusFlowService.cancel(purchaseCode);
	}
}
