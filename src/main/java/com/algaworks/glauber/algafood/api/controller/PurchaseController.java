package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.PurchaseModelAssembler;
import com.algaworks.glauber.algafood.api.assembler.PurchaseSummaryModelAssembler;
import com.algaworks.glauber.algafood.api.model.PurchaseModel;
import com.algaworks.glauber.algafood.api.model.PurchaseSummaryModel;
import com.algaworks.glauber.algafood.api.model.input.PurchaseInpurDisassembler;
import com.algaworks.glauber.algafood.api.model.input.PurchaseInput;
import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Purchase;
import com.algaworks.glauber.algafood.domain.model.User;
import com.algaworks.glauber.algafood.domain.repository.PurchaseRepository;
import com.algaworks.glauber.algafood.domain.service.PurchaseRegistrationService;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
	
	@Autowired
	private PurchaseRegistrationService purchaseRegistrationService;
	
	@Autowired
	private PurchaseModelAssembler purchaseModelAssembler;
	
	@Autowired
	private PurchaseInpurDisassembler purchaseInpurDisassembler;
	
	@Autowired
	private PurchaseSummaryModelAssembler purchaseSummaryModelAssembler;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@GetMapping
	public List<PurchaseSummaryModel> index() {
		return purchaseSummaryModelAssembler.toCollectionModel(purchaseRepository.findAll());
	}
	
	@GetMapping("/{purchaseCode}")
	public PurchaseModel show(@PathVariable String purchaseCode) {
		return purchaseModelAssembler
				.toModel(purchaseRegistrationService
						.findPurchaseByCodeOrElseThrow(purchaseCode));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PurchaseModel create(@RequestBody @Valid PurchaseInput purchaseInput) {
		try {
			Purchase purchase = purchaseInpurDisassembler.toDomainObject(purchaseInput);
			
			//TODO: pegar o usuário logado
			User client = new User();
			client.setId(1L);
			purchase.setClient(client);
			
			return purchaseModelAssembler.toModel(purchaseRegistrationService.save(purchase));
			
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e); 
		}
	}
}
