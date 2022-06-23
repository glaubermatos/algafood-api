package com.algaworks.glauber.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.glauber.algafood.AlgafoodApiApplication;
import com.algaworks.glauber.algafood.domain.model.PaymentMethod;
import com.algaworks.glauber.algafood.domain.repository.PaymentMethodRepository;

public class CadastroFormaPagamentoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
					.web(WebApplicationType.NONE)
					.run(args);
		
		PaymentMethodRepository paymentMethodRepository = applicationContext.getBean(PaymentMethodRepository.class);
		
		PaymentMethod payment = new PaymentMethod();
		payment.setDescription("Cartão de Crédito");
		
		payment = paymentMethodRepository.salvar(payment);
		System.out.printf("%d - %s\n", payment.getId(), payment.getDescription());
	}
}
