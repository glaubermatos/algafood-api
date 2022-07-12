package com.algaworks.glauber.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.model.PaymentMethod;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.model.User;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;
	
	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;
	
	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@Transactional
	public Restaurant salvar(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();		
		Long cityId = restaurant.getAddress().getCity().getId();
		
		Cuisine cuisine = cuisineRegistrationService.findCuisineByIdOrElseThrow(cuisineId);
		City city = cityRegistrationService.findCityByIdOrElseThrow(cityId);
		
		restaurant.setCuisine(cuisine);
		restaurant.getAddress().setCity(city);
		
		return restaurantRepository.save(restaurant);
	}
	
	@Transactional
	public void activate(Long restaurantId) {
		Restaurant restaurant = findRestaurantByIdOrElseThrow(restaurantId);
		restaurant.activate();
	}
	
	@Transactional
	public void inactivate(Long restaurantId) {
		Restaurant restaurant = findRestaurantByIdOrElseThrow(restaurantId);
		restaurant.inactivate();
	}
	
	@Transactional
	public void activate(List<Long> restaurantIds) {
//		restaurantIds.forEach(this::activate);
		restaurantIds.forEach(restaurantId -> activate(restaurantId));
	}
	
	@Transactional
	public void inactivate(List<Long> restaurantIds) {
//		restaurantIds.forEach(this::inactivate);
		restaurantIds.forEach(restaurantId -> inactivate(restaurantId));
	}
	

	//TODO: traduzir nome do método
	@Transactional
	public void associarFormaPagamento(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findRestaurantByIdOrElseThrow(restaurantId);
		PaymentMethod paymentMethod = paymentMethodRegistrationService.findPaymentMethodByIdOrElseThrow(paymentMethodId);
		
		restaurant.associarComFormaPagamento(paymentMethod);
	}
	
	//TODO: traduzir nome do método
	@Transactional
	public void desassociarFormaPagamento(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findRestaurantByIdOrElseThrow(restaurantId);
		PaymentMethod paymentMethod = paymentMethodRegistrationService.findPaymentMethodByIdOrElseThrow(paymentMethodId);
		
		restaurant.desassociarComFormaPagamento(paymentMethod);
	}

	@Transactional
	public void close(Long restaurantId) {
		Restaurant closingRestaurant = findRestaurantByIdOrElseThrow(restaurantId);
		closingRestaurant.close();
		
	}

	@Transactional
	public void open(Long restaurantId) {
		Restaurant openingRestaurant = findRestaurantByIdOrElseThrow(restaurantId);
		openingRestaurant.open();
		
	}
	
	@Transactional
	public void addUserResponsible(Long restaurantId, Long userResponsibleId) {
		Restaurant restaurant = findRestaurantByIdOrElseThrow(restaurantId);
		User userResponsible = userRegistrationService.findUserByIdOrElseThrow(userResponsibleId); 
		
		restaurant.addResponsible(userResponsible);
	}
	
	@Transactional
	public void removeResponsable(Long retaurantId, Long userResponsableId) {
		Restaurant restaurant = findRestaurantByIdOrElseThrow(retaurantId);
		User userReponsable = userRegistrationService.findUserByIdOrElseThrow(userResponsableId);
		
		restaurant.removeResponsible(userReponsable);
	}
	
	public Restaurant findRestaurantByIdOrElseThrow(Long id) {
		try {
			return restaurantRepository.findByIdOrElseThrow(id);
		} catch (EntityNotFoundException e) {
			throw new RestaurantNotFoundException(id);
		}
	}
}
