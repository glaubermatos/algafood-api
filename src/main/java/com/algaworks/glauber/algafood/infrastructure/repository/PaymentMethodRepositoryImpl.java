package com.algaworks.glauber.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.domain.model.PaymentMethod;
import com.algaworks.glauber.algafood.domain.repository.PaymentMethodRepository;

@Component
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<PaymentMethod> listar() {
		return entityManager.createQuery("from PaymentMethod", PaymentMethod.class)
				.getResultList();
	}

	@Override
	public PaymentMethod buscar(Long id) {
		return entityManager.find(PaymentMethod.class, id);
	}

	@Transactional
	@Override
	public PaymentMethod salvar(PaymentMethod paymentMethod) {
		return entityManager.merge(paymentMethod);
	}

	@Transactional
	@Override
	public void remover(PaymentMethod paymentMethod) {
		paymentMethod = buscar(paymentMethod.getId());
		entityManager.remove(paymentMethod);
	}

}
