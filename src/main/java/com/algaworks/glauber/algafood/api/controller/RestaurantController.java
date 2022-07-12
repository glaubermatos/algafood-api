package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.RestaurantInputDisassembler;
import com.algaworks.glauber.algafood.api.assembler.RestaurantModelAssembler;
import com.algaworks.glauber.algafood.api.model.RestaurantModel;
import com.algaworks.glauber.algafood.api.model.input.RestaurantInput;
import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.CityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.CuisineNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private RestaurantModelAssembler restaurantModelAssembler;
	
	@Autowired
	private RestaurantInputDisassembler restaurantInputDisassembler;

	@GetMapping
	public List<RestaurantModel> listar() {
		return restaurantModelAssembler.toCollectionModel(restaurantRepository.findAll());
	}

	@GetMapping("/{restaurantId}")
	public RestaurantModel buscar(@PathVariable Long restaurantId) {
//		if (true) {
//			throw new IllegalArgumentException("teste");
//		}
		return restaurantModelAssembler.toModel(restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantModel criar(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			return restaurantModelAssembler.toModel(restaurantRegistrationService.salvar(restaurantInputDisassembler.toDomainObject(restaurantInput)));
		} catch (CuisineNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restaurantId}")
	public RestaurantModel atualizar(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput) {
		Restaurant currentRestaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		
		restaurantInputDisassembler.copyToDomainObject(restaurantInput, currentRestaurant);
//		BeanUtils.copyProperties(restaurant, currentRestaurant, 
//				"id", "paymentMethods", "address", "createdAt", "products");
		
		try {
			return restaurantModelAssembler.toModel(restaurantRegistrationService.salvar(currentRestaurant));
		} catch (CuisineNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}		
	}
	
//	ATUALIZAR PARCIAL
//	@PatchMapping("/{restaurantId}")
//	public Restaurant atualizarParcial(@PathVariable Long restaurantId, @RequestBody Map<String, Object> requestFields, HttpServletRequest request) {
//		Restaurant currentRestaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
//		
//		merge(requestFields, currentRestaurant, request);
//		
//		return atualizar(restaurantId, currentRestaurant);
//	}
//
//	private void merge(Map<String, Object> requestFields, Restaurant restaurantTarget, HttpServletRequest request) {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//			
//			Restaurant dataRestaurant = objectMapper.convertValue(requestFields, Restaurant.class);
//			
//			requestFields.forEach((propertie, value) -> {
//				Field field = ReflectionUtils.findField(Restaurant.class, propertie);
//				field.setAccessible(true);
//				
//				Object objectValue = ReflectionUtils.getField(field, dataRestaurant);
//				
//				ReflectionUtils.setField(field, restaurantTarget, objectValue);
//			});
//		} catch (IllegalArgumentException e) {
//			HttpInputMessage httpInputMessage = new ServletServerHttpRequest(request);
//			throw new HttpMessageNotReadableException(e.getMessage(), ExceptionUtils.getRootCause(e), httpInputMessage);
//		}
//	}
	
	@PutMapping("/{restaurantId}/closure")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void closure(@PathVariable Long restaurantId) {
		restaurantRegistrationService.close(restaurantId);
	}
	
	@PutMapping("/{restaurantId}/opening")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void opening(@PathVariable Long restaurantId) {
		restaurantRegistrationService.open(restaurantId);
	}
	
	@PutMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT	)
	public void activate(@PathVariable Long restaurantId) {
		restaurantRegistrationService.activate(restaurantId);
	}
	
	@DeleteMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivate(@PathVariable Long restaurantId) {
		restaurantRegistrationService.inactivate(restaurantId);
	}
	
	@PutMapping("/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activateMultiple(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistrationService.activate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT	)
	public void inactivateMultiple(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistrationService.inactivate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
