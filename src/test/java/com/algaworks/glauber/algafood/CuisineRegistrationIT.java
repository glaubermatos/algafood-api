package com.algaworks.glauber.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.service.CuisineRegistrationService;

@SpringBootTest
class CuisineRegistrationIT {

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;
	
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
		Long cuisineId = 1L;
		
		//ação
		EntityInUseException expectedError = 
				Assertions.assertThrows(EntityInUseException.class, () -> {
					cuisineRegistrationService.excluir(cuisineId);
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

}
