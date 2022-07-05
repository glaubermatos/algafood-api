package com.algaworks.glauber.algafood;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.glauber.algafood.api.exceptionhandler.problem.ProblemType;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.model.Restaurant;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;
import com.algaworks.glauber.algafood.domain.repository.RestaurantRepository;
import com.algaworks.glauber.algafood.util.DatabaseCleaner;
import com.algaworks.glauber.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantRegistrationApiTestIT {
	
	private static final Long RESTAURANT_ID_NOT_FOUND = 100L;
	public static final String BUSINESS_RULE_VIOLATION_PROBLEM_TYPE = "Violação de regra de negócio";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	private Restaurant burguerTopRestaurant;
	private int numberOfRegisteredRestaurants;
	private String jsonRestaurantWithDataValid;
	private String jsonRestaurantWithoutName;
	private String jsonRestaurantWithoutFreightRate;
	private String jsonRestaurantWithoutCuisine;
	private String jsonRestaurantWithoutCuisineId;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurants";
		
		databaseCleaner.clearTables();
		jsonRestaurantWithDataValid = ResourceUtils.getContentFromResource("/json/restaurant/restaurant-new-york-barbecue-correct-data.json");
		jsonRestaurantWithoutName = ResourceUtils.getContentFromResource("/json/restaurant/restaurant-new-york-barbecue-without-name.json");
		jsonRestaurantWithoutFreightRate = ResourceUtils.getContentFromResource("/json/restaurant/restaurant-new-york-barbecue-without-freightrate.json");
		jsonRestaurantWithoutCuisine = ResourceUtils.getContentFromResource("/json/restaurant/restaurant-new-york-barbecue-without-cuisine.json");
		jsonRestaurantWithoutCuisineId = ResourceUtils.getContentFromResource("/json/restaurant/restaurant-new-york-barbecue-without-cuisine-id.json");
		
		prepareData();
	}
	
	@Test
	public void shouldReturnStatus200_WhenFindRestaurants() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldReturnCorrectNumberOfRestaurants() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(numberOfRegisteredRestaurants))
			.body("name", Matchers.hasItems(burguerTopRestaurant.getName()));
	}
	
	@Test
	public void shouldReturnStatusAndResponseCorrect_WhenFindRestaurantExisting() {
		RestAssured.given()
			.pathParam("restaurantId", burguerTopRestaurant.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restaurantId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", Matchers.equalTo(burguerTopRestaurant.getName()));
	}
	
	@Test
	public void shouldReturnStatus404_WhenFindRestaurantNotFound() {
		RestAssured.given()
			.pathParam("restaurantId", RESTAURANT_ID_NOT_FOUND)
			.accept(ContentType.JSON)
		.when()
			.get("/{restaurantId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldReturnStatus201_WhenRegistrationRestauranteWithCorrectData() {
		RestAssured.given()
			.body(jsonRestaurantWithDataValid)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldReturnStatus400_WhenRegistrationRestauranteWithoutName() {
		RestAssured.given()
			.body(jsonRestaurantWithoutName)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", Matchers.equalTo(ProblemType.INVALID_DATA.getTitle()));
	}
	
	@Test
	public void shouldReturnStatus400_WhenRegistrationRestauranteWithoutFreightRate() {
		RestAssured.given()
			.body(jsonRestaurantWithoutFreightRate)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", Matchers.equalTo(ProblemType.INVALID_DATA.getTitle()));
	}
	
	@Test
	public void shouldReturnStatus400_WhenRegistrationRestauranteWithoutCuisine() {
		RestAssured.given()
			.body(jsonRestaurantWithoutCuisine)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", Matchers.equalTo(ProblemType.INVALID_DATA.getTitle()));
	}
	
	@Test
	public void shouldReturnStatus400_WhenRegistrationRestauranteWithoutCuisineId() {
		RestAssured.given()
			.body(jsonRestaurantWithoutCuisineId)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", Matchers.equalTo(ProblemType.INVALID_DATA.getTitle()));
	}
		
	private void prepareData() {
		Cuisine cuisineBrasileira = new Cuisine();
		cuisineBrasileira.setName("Brasileira");
		
		cuisineRepository.save(cuisineBrasileira);
		
		Cuisine cuisineAmericana = new Cuisine();
		cuisineAmericana.setName("Americana");
		
		cuisineRepository.save(cuisineAmericana);

		burguerTopRestaurant = new Restaurant();
		burguerTopRestaurant.setName("Burguer Top");
		burguerTopRestaurant.setFreightRate(new BigDecimal(10));
		burguerTopRestaurant.setCuisine(cuisineAmericana);
		
		restaurantRepository.save(burguerTopRestaurant);
		
		Restaurant comidaMineiraRestaurant = new Restaurant();
		comidaMineiraRestaurant.setName("Comida Mineira");
		comidaMineiraRestaurant.setFreightRate(new BigDecimal(10));
		comidaMineiraRestaurant.setCuisine(cuisineBrasileira);
		
		restaurantRepository.save(comidaMineiraRestaurant);
		
		numberOfRegisteredRestaurants = (int) restaurantRepository.count();
	}

}
