package com.algaworks.glauber.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.glauber.algafood.AlgafoodApiApplication;
import com.algaworks.glauber.algafood.domain.model.PurchaseItem;
import com.algaworks.glauber.algafood.domain.repository.PurchaseItemRepository;

public class ConsultaItensDoPedidoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
					.web(WebApplicationType.NONE)
					.run(args);
		
		PurchaseItemRepository purchaseItemRepository = applicationContext.getBean(PurchaseItemRepository.class);
		
		List<PurchaseItem> itens = purchaseItemRepository.findAll();
		
		System.out.println(itens.size());
	}
}
