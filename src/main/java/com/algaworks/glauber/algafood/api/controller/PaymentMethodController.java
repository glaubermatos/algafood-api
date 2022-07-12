package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.PaymentMethodInputDisassembler;
import com.algaworks.glauber.algafood.api.assembler.PaymentMethodModelAssembler;
import com.algaworks.glauber.algafood.api.model.PaymentMethodModel;
import com.algaworks.glauber.algafood.api.model.input.PaymentMethodInput;
import com.algaworks.glauber.algafood.domain.model.PaymentMethod;
import com.algaworks.glauber.algafood.domain.repository.PaymentMethodRepository;
import com.algaworks.glauber.algafood.domain.service.PaymentMethodRegistrationService;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {
	
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	private PaymentMethodModelAssembler paymentMethodModelAssembler;
	
	@Autowired
	private PaymentMethodInputDisassembler paymentMethodInputDisassembler;
	
	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;

	@GetMapping
	public List<PaymentMethodModel> index() {
		return paymentMethodModelAssembler
				.toCollectionModel(paymentMethodRepository.findAll());
	}
	
	@GetMapping("/{paymentMethodId}")
	public PaymentMethodModel show(@PathVariable Long paymentMethodId) {
		return paymentMethodModelAssembler
				.toModel(paymentMethodRegistrationService
						.findPaymentMethodByIdOrElseThrow(paymentMethodId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentMethodModel create(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		return paymentMethodModelAssembler
				.toModel(paymentMethodRegistrationService
						.save(paymentMethodInputDisassembler
								.toDomainObject(paymentMethodInput)));
	}
	
	@PutMapping("/{paymentMethodId}")
	public PaymentMethodModel update(@PathVariable Long paymentMethodId, @RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		PaymentMethod currentPaymentMethod = paymentMethodRegistrationService.findPaymentMethodByIdOrElseThrow(paymentMethodId);
		
		paymentMethodInputDisassembler.copyToDomainObject(paymentMethodInput, currentPaymentMethod);
		
		return paymentMethodModelAssembler.toModel(paymentMethodRegistrationService.save(currentPaymentMethod));
	}
	
	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long paymentMethodId) {
		paymentMethodRegistrationService.delete(paymentMethodId);
	}
}
