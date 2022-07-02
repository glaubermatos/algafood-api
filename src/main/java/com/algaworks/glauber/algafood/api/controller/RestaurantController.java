package com.algaworks.glauber.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.CuisineNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
		return restaurantRepository.findAll();
	}
	
	@GetMapping("/{restaurantId}")
	public Restaurant buscar(@PathVariable Long restaurantId) {
		if (true) {
			throw new IllegalArgumentException("teste");
		}
		return restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant criar(@RequestBody Restaurant restaurant) {
		try {
			return restaurantRegistrationService.salvar(restaurant);
		} catch (CuisineNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restaurantId}")
	public Restaurant atualizar(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		Restaurant currentRestaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		
		BeanUtils.copyProperties(restaurant, currentRestaurant, 
				"id", "paymentMethods", "address", "createdAt", "products");
		
		try {
			return restaurantRegistrationService.salvar(currentRestaurant);
		} catch (CuisineNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}		
	}
	
	@PatchMapping("/{restaurantId}")
	public Restaurant atualizarParcial(@PathVariable Long restaurantId, @RequestBody Map<String, Object> requestFields, HttpServletRequest request) {
		Restaurant currentRestaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		
		merge(requestFields, currentRestaurant, request);
		
		return atualizar(restaurantId, currentRestaurant);
	}

	private void merge(Map<String, Object> requestFields, Restaurant restaurantTarget, HttpServletRequest request) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurant dataRestaurant = objectMapper.convertValue(requestFields, Restaurant.class);
			
			requestFields.forEach((propertie, value) -> {
				Field field = ReflectionUtils.findField(Restaurant.class, propertie);
				field.setAccessible(true);
				
				Object objectValue = ReflectionUtils.getField(field, dataRestaurant);
				
				ReflectionUtils.setField(field, restaurantTarget, objectValue);
			});
		} catch (IllegalArgumentException e) {
			HttpInputMessage httpInputMessage = new ServletServerHttpRequest(request);
			throw new HttpMessageNotReadableException(e.getMessage(), ExceptionUtils.getRootCause(e), httpInputMessage);
		}
	}
}
