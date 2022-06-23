package com.algaworks.glauber.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.glauber.algafood.AlgafoodApiApplication;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.repository.CityRepository;

public class ConsultaPermissionMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
					.web(WebApplicationType.NONE)
					.run(args);
		
		CityRepository cityRepository = applicationContext.getBean(CityRepository.class);
		
		List<City> cities = cityRepository.listar();
		
		cities.stream().forEach(city -> System.out.printf("%d - %s - %s\n", city.getId(), city.getName(), city.getState().getName()));
	}
}
