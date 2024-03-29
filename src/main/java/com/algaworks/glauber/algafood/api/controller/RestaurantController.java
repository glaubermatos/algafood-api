package com.algaworks.glauber.algafood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.algaworks.glauber.algafood.api.model.view.RestaurantView;
import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.CityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.CuisineNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.annotation.JsonView;

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

	@JsonView(RestaurantView.Summary.class)
	@GetMapping
	public ResponseEntity<List<RestaurantModel>> index() {
		
		List<RestaurantModel> restaurantesModel = restaurantModelAssembler
				.toCollectionModel(restaurantRepository.findAll());
		
		
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()) // permite apenas cache local no navegador do cliente
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()) //permite cache intermediário, um cache compartilhado
//				.cacheControl(CacheControl.noCache()) // se a resposta for cacheada vai ser necessário sempre uma validação no servidor, passa a ser obrigatória, a representação no cache local fica sempre stale
//				.cacheControl(CacheControl.noStore()) //esse sim não permite armazenar o cache da resposta
				.body(restaurantesModel);
	}

	@JsonView(RestaurantView.JustName.class)
	@GetMapping(params = "projection=just_name")
	public ResponseEntity<List<RestaurantModel>> indexJustName() {
		return index();
	}

//	@GetMapping
//	public MappingJacksonValue index(@RequestParam(required = false) String projection) {
//		List<Restaurant> restaurants = restaurantRepository.findAll();
//		List<RestaurantModel> restaurantsModel = restaurantModelAssembler.toCollectionModel(restaurants);
//		
//		MappingJacksonValue restaurantsWrapper = new MappingJacksonValue(restaurantsModel);
//		restaurantsWrapper.setSerializationView(RestaurantView.Summary.class);
//		
//		if ("just_name".equals(projection)) {
//			restaurantsWrapper.setSerializationView(RestaurantView.JustName.class);
//			
//		} else if ("complete".equals(projection)) {
//			restaurantsWrapper.setSerializationView(null);
//		}
//		
//		return restaurantsWrapper;
//	}
	
//	@GetMapping
//	public List<RestaurantModel> index() {
//		return restaurantModelAssembler.toCollectionModel(restaurantRepository.findAll());
//	}
//
//	@JsonView(RestaurantView.Summary.class)
//	@GetMapping(params = "projection=summary")
//	public List<RestaurantModel> indexSummary() {
//		return index();
//	}
	
	@GetMapping("/{restaurantId}")
	public ResponseEntity<RestaurantModel> show(@PathVariable Long restaurantId) {
//		if (true) {
//			throw new IllegalArgumentException("teste");
//		}
		RestaurantModel restaurantModel = restaurantModelAssembler.toModel(restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId));
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(restaurantModel);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantModel create(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			return restaurantModelAssembler.toModel(restaurantRegistrationService.salvar(restaurantInputDisassembler.toDomainObject(restaurantInput)));
		} catch (CuisineNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restaurantId}")
	public RestaurantModel update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput) {
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
