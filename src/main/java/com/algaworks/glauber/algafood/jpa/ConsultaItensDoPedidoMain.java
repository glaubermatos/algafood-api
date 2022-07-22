package com.algaworks.glauber.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.glauber.algafood.AlgafoodApiApplication;
import com.algaworks.glauber.algafood.domain.model.Purchase;
import com.algaworks.glauber.algafood.domain.repository.PurchaseRepository;

public class ConsultaItensDoPedidoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
					.web(WebApplicationType.NONE)
					.run(args);
		
		PurchaseRepository purchaseRepository = applicationContext.getBean(PurchaseRepository.class);
		
		Purchase purchase = purchaseRepository.findById(1L).orElseThrow(() -> new RuntimeException());
		
		System.out.println(purchase.getItems().size());
	}
}
