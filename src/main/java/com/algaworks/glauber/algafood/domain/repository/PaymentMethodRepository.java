package com.algaworks.glauber.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends CustomJpaRepository<PaymentMethod, Long>{

	@Query("select max(updatedAt) from PaymentMethod")
	OffsetDateTime getLastUpdatedAt();
	
	@Query("select updatedAt from PaymentMethod where id = :paymentMethodId")
	OffsetDateTime getLastUpdatedAtById(Long paymentMethodId);
}
