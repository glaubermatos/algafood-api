package com.algaworks.glauber.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.glauber.algafood.domain.model.Purchase;

@Repository
public interface PurchaseRepository extends CustomJpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {

//	Quando uso o Spacification no método findAll(usingFilter(filter)) acaba não usando essa implementação
	@Query("from Purchase p join fetch p.client join fetch p.restaurant r join fetch r.cuisine")
	List<Purchase> findAll();
	
//	@Query("from Purchase p "
//			+ "join fetch p.client "
//			+ "join fetch p.restaurant "
//			+ "join fetch p.restaurant.cuisine "
//			+ "left join fetch p.address.city "
//			+ "join fetch p.address.city.state "
//			+ "join fetch p.items i "
//			+ "join fetch i.product "
//			+ "join fetch p.paymentMethod "
//			+ "where p.code = :code")
	Optional<Purchase> findByCode(String code);
}
