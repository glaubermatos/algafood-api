package com.algaworks.glauber.algafood.domain.service;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_IN_USE;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.PaymentMethodNotFoundException;
import com.algaworks.glauber.algafood.domain.model.PaymentMethod;
import com.algaworks.glauber.algafood.domain.repository.PaymentMethodRepository;

@Service
public class PaymentMethodRegistrationService {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@Transactional
	public PaymentMethod save(PaymentMethod paymentMethod) {
		return paymentMethodRepository.save(paymentMethod);
	}
	
	@Transactional
	public void delete(Long paymentMethodId) {
		try {
			paymentMethodRepository.deleteById(paymentMethodId);
			paymentMethodRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new PaymentMethodNotFoundException(paymentMethodId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, "Método de pagamento", paymentMethodId));
		}
	}

	public PaymentMethod findPaymentMethodByIdOrElseThrow(Long paymentMethodId) {
		try {
			return paymentMethodRepository.findByIdOrElseThrow(paymentMethodId);
		} catch (EntityNotFoundException e) {
			throw new PaymentMethodNotFoundException(paymentMethodId);
		}
	}
	
}
