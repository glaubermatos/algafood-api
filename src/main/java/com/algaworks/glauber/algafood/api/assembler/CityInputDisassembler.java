package com.algaworks.glauber.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.glauber.algafood.api.model.input.CityInput;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.model.State;

@Component
public class CityInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}

	public void copyToDomainObject(CityInput cityInput, City currentCity) {
		//para evitar exceção Caused by: org.hibernate.HibernateException: identifier of an instance 
		//of com.algaworks.glauber.algafood.domain.model.State was altered from 1 to 10
		currentCity.setState(new State());
		
		modelMapper.map(cityInput, currentCity);
	}
}
