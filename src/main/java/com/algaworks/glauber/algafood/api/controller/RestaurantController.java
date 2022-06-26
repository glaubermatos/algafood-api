package com.algaworks.glauber.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@GetMapping
	public List<Restaurant> listar() {
		return restaurantRepository.listar();
	}
	
	@GetMapping("/{restaurantId}")
	public ResponseEntity<Restaurant> buscar(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRepository.buscar(restaurantId);
		
		if (restaurant == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(restaurant);
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@RequestBody Restaurant restaurant) {
		try {
			restaurant = restaurantRegistrationService.salvar(restaurant);
			return ResponseEntity.ok(restaurant);
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/{restaurantId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		Restaurant currentRestaurant = restaurantRepository.buscar(restaurantId);
		
		if (currentRestaurant == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(restaurant, currentRestaurant, "id");
		
		try {
			currentRestaurant = restaurantRegistrationService.salvar(currentRestaurant);			
			return ResponseEntity.ok(currentRestaurant);
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restaurantId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restaurantId, @RequestBody Map<String, Object> requestFields) {
		Restaurant currentRestaurant = restaurantRepository.buscar(restaurantId);
		
		if (currentRestaurant == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(requestFields, currentRestaurant);
		
		return atualizar(restaurantId, currentRestaurant);
	}

	private void merge(Map<String, Object> requestFields, Restaurant restaurantTarget) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurant dataRestaurant = objectMapper.convertValue(requestFields, Restaurant.class);
		
		requestFields.forEach((propertie, value) -> {
			Field field = ReflectionUtils.findField(Restaurant.class, propertie);
			field.setAccessible(true);
			
			Object objectValue = ReflectionUtils.getField(field, dataRestaurant);
			
			ReflectionUtils.setField(field, restaurantTarget, objectValue);
		});
	}
}
