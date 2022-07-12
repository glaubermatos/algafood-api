package com.algaworks.glauber.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Address;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.model.State;
import com.algaworks.glauber.algafood.domain.service.CityRegistrationService;
import com.algaworks.glauber.algafood.domain.service.CuisineRegistrationService;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;
import com.algaworks.glauber.algafood.domain.service.StateRegistrationService;
import com.algaworks.glauber.algafood.util.DatabaseCleaner;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class CuisineRegistrationIT {

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	@Autowired
	private StateRegistrationService stateRegistrationService;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private Cuisine cuisineJaponesa;
	
	@BeforeEach
	public void setUp() {
		databaseCleaner.clearTables();
	}
	
	@Test
	void shouldAssignId_WhenRegisteringCuisineWithCorrectData() {
		//cenário
		String cuisineName = "Japonesa";
		
		Cuisine cuisine = new Cuisine();
		cuisine.setName(cuisineName);
		
		//ação
		cuisine = cuisineRegistrationService.salvar(cuisine);
		
		//validação
//		assertNotNull(cuisine.getId());
//		assertEquals(cuisineName, cuisine.getName());
		
		assertThat(cuisine).isNotNull();
		assertThat(cuisine.getId()).isNotNull();
		assertThat(cuisine.getName()).isEqualTo(cuisineName);
	}
	
	@Test
	public void shouldFail_WhenRegisteringUnnamedCuisine() {
		Cuisine cuisine = new Cuisine();
		cuisine.setName(null);
		
		ConstraintViolationException expectedError = 
				Assertions.assertThrows(ConstraintViolationException.class, () -> {
					cuisineRegistrationService.salvar(cuisine);
				});
		
		assertThat(expectedError).isNotNull();
		
	}
	
	@Test
	public void shouldFail_WhenDeleteCuisineInUse() {
		//cenário
		prepareData();		
		System.out.println("=====================> cuisineJaponesa.id "+cuisineJaponesa.getId());
		//ação
		EntityInUseException expectedError = 
				Assertions.assertThrows(EntityInUseException.class, () -> {
					cuisineRegistrationService.excluir(cuisineJaponesa.getId());
				});
		
		//validação
		assertThat(expectedError).isNotNull();
	}
	
	@Test
	public void shoulFail_WhenDeleteCuisineNotFound() {
		//cenário
		Long cuisineId = 1000L;
		
		//ação
		EntityNotFoundException expectedError = 
				Assertions.assertThrows(EntityNotFoundException.class, () -> {
					cuisineRegistrationService.excluir(cuisineId);
				});
		
		//validação
		assertThat(expectedError).isNotNull();
	}
	
	public void prepareData() {
		cuisineJaponesa = new Cuisine();
		cuisineJaponesa.setName("Japonesa");
		
		cuisineJaponesa = cuisineRegistrationService.salvar(cuisineJaponesa);
		
		
		Restaurant burguerTopRestaurant = new Restaurant();
		burguerTopRestaurant.setName("Burguer Top");
		burguerTopRestaurant.setFreightRate(new BigDecimal("11.5"));
		burguerTopRestaurant.setCuisine(cuisineJaponesa);
		
		State state = new State();
		state.setName("Bahia");		
		state = stateRegistrationService.salvar(state);
		
		City city = new City();
		city.setName("Jaguaquara");
		city.setState(state);
		city = cityRegistrationService.salvar(city);
			
		
		Address address = new Address();
		address.setCity(city);
		address.setDistrict("Palmeira");
		address.setNumber("890");
		address.setPostalCode("45340-000");
		address.setStreet("Rua Pedro Santos");
		
		burguerTopRestaurant.setAddress(address);
		
		restaurantRegistrationService.salvar(burguerTopRestaurant);
	}

}
