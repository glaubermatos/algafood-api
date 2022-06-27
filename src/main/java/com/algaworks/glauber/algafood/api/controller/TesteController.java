package com.algaworks.glauber.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CuisineRepository cuisineRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cuisine> listarPorNome(@RequestParam String name) {
		return cuisineRepository.findByNameContaining(name);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cuisine> cozinhaPorNome(@RequestParam String name) {
		return cuisineRepository.findByName(name);
	}
	
	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExiste(@RequestParam String name) {
		return cuisineRepository.existsByName(name);
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurant> porTaxaFrete(@RequestParam BigDecimal freightRateInitial, BigDecimal freightRateFinal) {
		return restaurantRepository.findByFreightRateBetween(freightRateInitial, freightRateFinal);
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurant> primeiroPorNome(@RequestParam String name) {
		return restaurantRepository.findFirstByNameContaining(name);
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurant> top2PorNome(@RequestParam String name) {
		return restaurantRepository.findTop2ByNameContaining(name);
	}
	
	@GetMapping("/restaurantes/count-por-cozinha")
	public int countPorCozinha(@RequestParam Long cuisineId) {
		return restaurantRepository.countByCuisineId(cuisineId);
	}
	
	@GetMapping("/restaurantes/por-nome-cozinha")
	public List<Restaurant> porNomeECozinha(@RequestParam String name, @RequestParam Long cuisineId) {
		return restaurantRepository.consultarPorNome(name, cuisineId);
	}
	
	@GetMapping("/restaurantes/por-nome-taxa-frete")
	public List<Restaurant> porNomeEFrete(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal) {
		return restaurantRepository.find(name, freightRateInitial, freightRateFinal);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurant> restaurantesComFreteGratis(String name) {
//		var comFreteGratis = RestaurantSpecifications.comFreteGratis();
//		var comNomeSemelhante = RestaurantSpecifications.comNomeSemelhante(name);
		
		return restaurantRepository.findComFreteGratis(name);
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurant> buscarPrimeiroRestaurante() {
		return restaurantRepository.buscarPrimeiro();
	}
	
	@GetMapping("/cozinhas/primeira")
	public Optional<Cuisine> buscarPrimeiraCozinha() {
		return cuisineRepository.buscarPrimeiro();
	}
}
