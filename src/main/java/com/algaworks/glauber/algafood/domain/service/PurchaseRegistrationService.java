package com.algaworks.glauber.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.PurchaseNotFoundException;
import com.algaworks.glauber.algafood.domain.model.City;
import com.algaworks.glauber.algafood.domain.model.PaymentMethod;
import com.algaworks.glauber.algafood.domain.model.Product;
import com.algaworks.glauber.algafood.domain.model.Purchase;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.model.User;
import com.algaworks.glauber.algafood.domain.repository.PurchaseRepository;

@Service
public class PurchaseRegistrationService {

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;
	
	@Autowired
	private ProductRegistrationService productRegistrationService;
	
	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@Transactional
	public Purchase save(Purchase purchase) {
		validatePurchase(purchase);		
		validateItems(purchase);
		

		purchase.setFreightRate(purchase.getRestaurant().getFreightRate());
		purchase.calculatePuchase();
		
		return purchaseRepository.save(purchase);
	}

	private void validateItems(Purchase purchase) {
		purchase.getItems()
			.forEach(item -> {
				Product product = productRegistrationService
						.findProductByIdAndRestaurantOrElseThrow(purchase.getRestaurant().getId(), item.getProduct().getId());
				
				item.setProduct(product);
				item.setUnitPrice(product.getPrice());
				item.setPurchase(purchase);
			});
	}

	private void validatePurchase(Purchase purchase) {
		Long restaurantId = purchase.getRestaurant().getId();
		Long paymentMethodId = purchase.getPaymentMethod().getId();
		Long cityId = purchase.getAddress().getCity().getId();
		Long clientId = purchase.getClient().getId();
		
		Restaurant restaurant = restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
		PaymentMethod paymentMethod = paymentMethodRegistrationService.findPaymentMethodByIdOrElseThrow(paymentMethodId);
		User client = userRegistrationService.findUserByIdOrElseThrow(clientId);
		City city = cityRegistrationService.findCityByIdOrElseThrow(cityId);
		
		if (restaurant.notAcceptPaymentMethod(paymentMethod)) {
			throw new BusinessException(String.format("%s não aceito como forma de pagamento pelo restaurante de id %d", paymentMethod.getDescription(), restaurantId));
		}
		
		purchase.setRestaurant(restaurant);
		purchase.setPaymentMethod(paymentMethod);
		purchase.setClient(client);
		purchase.getAddress().setCity(city);
	}
	
	//trocou a implementação da busca de id pelo codigo uuid
//	public Purchase findPurchaseByIdOrElseThrow(Long purchaseId) {
//		try {
//			return purchaseRepository.findByIdOrElseThrow(purchaseId);
//		} catch (EntityNotFoundException e) {
//			throw new PurchaseNotFoundException(purchaseId);
//		}
//	}
	
	public Purchase findPurchaseByCodeOrElseThrow(String purchaseCode) {
		return purchaseRepository.findByCode(purchaseCode)
				.orElseThrow(() -> new PurchaseNotFoundException(purchaseCode));
	}
}
