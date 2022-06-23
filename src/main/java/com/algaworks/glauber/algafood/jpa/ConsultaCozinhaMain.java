package com.algaworks.glauber.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.glauber.algafood.AlgafoodApiApplication;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
					.web(WebApplicationType.NONE)
					.run(args);
		
		CuisineRepository cuisineRepository = applicationContext.getBean(CuisineRepository.class);
		
		List<Cuisine> cozinhas = cuisineRepository.listar();
		
		cozinhas.stream().forEach(cozinha -> System.out.println(cozinha.getName()));
	}
}
