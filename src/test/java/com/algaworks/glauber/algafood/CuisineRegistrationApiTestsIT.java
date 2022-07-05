package com.algaworks.glauber.algafood;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;
import com.algaworks.glauber.algafood.util.DatabaseCleaner;
import com.algaworks.glauber.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CuisineRegistrationApiTestsIT {
	
	private static final int CUISINE_NOT_FOUND_ID = 100;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	private int numberOfRegisteredCuisine;	
	private Cuisine cuisineIndiana;
	private Cuisine cuisineTailandesa;	
	private String jsonCuisineWithDataValid;
	
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cuisines";
		
		databaseCleaner.clearTables();
		jsonCuisineWithDataValid = ResourceUtils.getContentFromResource("/json/cuisine/valid-data.json");
		
		prepareData();
	}

	@Test
	public void shouldReturnHttpStatus200_WhenFindCuisines() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldReturnCorrectNumberOfCuisines() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(numberOfRegisteredCuisine))
			.body("name", Matchers.hasItems(cuisineIndiana.getName(), cuisineTailandesa.getName()));
		
	}
	
	@Test
	public void shouldReturnStatus201_WhenRegistrationCuisine() {
		RestAssured.given()
			.body(jsonCuisineWithDataValid)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shoulReturnStatusAndResponseCorrect_WhenFindCuisineExisting() {
		RestAssured.given()
			.pathParam("cuisineId", cuisineIndiana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cuisineId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", Matchers.equalTo(cuisineIndiana.getName()));
	}
	
	@Test
	public void shouldReturnStatus404_WhenFindCuisineNotFound() {
		RestAssured.given()
			.pathParam("cuisineId", CUISINE_NOT_FOUND_ID)
			.accept(ContentType.JSON)
		.when()
			.get("/{cuisineId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepareData() {
		cuisineIndiana = new Cuisine();
		cuisineIndiana.setName("Indiana");
		cuisineRepository.save(cuisineIndiana);	
		
		cuisineTailandesa = new Cuisine();
		cuisineTailandesa.setName("Tailandesa");
		cuisineRepository.save(cuisineTailandesa);
		
		numberOfRegisteredCuisine = (int) cuisineRepository.count();
	}
}