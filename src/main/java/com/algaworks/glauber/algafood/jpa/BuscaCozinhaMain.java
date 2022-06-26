package com.algaworks.glauber.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.glauber.algafood.AlgafoodApiApplication;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;

public class BuscaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
					.web(WebApplicationType.NONE)
					.run(args);
		
		CuisineRepository cuisineRepository = applicationContext.getBean(CuisineRepository.class);
		
		Cuisine cozinha = cuisineRepository.buscar(1L);
		System.out.println(cozinha.getName());
	}
}