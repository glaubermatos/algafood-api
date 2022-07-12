package com.algaworks.glauber.algafood.domain.repository;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends CustomJpaRepository<PaymentMethod, Long>{

}
