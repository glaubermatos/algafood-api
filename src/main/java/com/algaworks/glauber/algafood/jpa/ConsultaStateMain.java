package com.algaworks.glauber.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.glauber.algafood.AlgafoodApiApplication;
import com.algaworks.glauber.algafood.domain.model.State;
import com.algaworks.glauber.algafood.domain.repository.StateRepository;

public class ConsultaStateMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
					.web(WebApplicationType.NONE)
					.run(args);
		
		StateRepository stateRepository = applicationContext.getBean(StateRepository.class);
		
		List<State> states = stateRepository.listar();
		
		states.stream().forEach(state -> System.out.printf("%d - %s\n", state.getId(), state.getName()));
	}
}
