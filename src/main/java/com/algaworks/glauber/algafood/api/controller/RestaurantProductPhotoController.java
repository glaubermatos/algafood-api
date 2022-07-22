package com.algaworks.glauber.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.glauber.algafood.api.assembler.ProductPhotoModelAssembler;
import com.algaworks.glauber.algafood.api.model.ProductPhotoModel;
import com.algaworks.glauber.algafood.api.model.input.ProductPhotoInput;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.model.Product;
import com.algaworks.glauber.algafood.domain.model.ProductPhoto;
import com.algaworks.glauber.algafood.domain.service.ProductPhotoCatalogService;
import com.algaworks.glauber.algafood.domain.service.ProductPhotoStorageService;
import com.algaworks.glauber.algafood.domain.service.ProductPhotoStorageService.PhotoRecover;
import com.algaworks.glauber.algafood.domain.service.ProductRegistrationService;
import com.algaworks.glauber.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {
	
	@Autowired
	private ProductPhotoCatalogService productPhotoCatalogService;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private ProductRegistrationService productRegistrationService;
	
	@Autowired
	private ProductPhotoModelAssembler productPhotoModelAssembler;
	
	@Autowired
	private ProductPhotoStorageService productPhotoStorageService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductPhotoModel updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid ProductPhotoInput productPhotoInput) throws IOException {
			restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
			Product product = productRegistrationService.findProductByIdAndRestaurantOrElseThrow(restaurantId, productId);
			
			MultipartFile file = productPhotoInput.getFile();
			
			ProductPhoto photo = new ProductPhoto();
			photo.setProduct(product);
			photo.setDescription(productPhotoInput.getDescription());
			photo.setContentType(file.getContentType());
			photo.setSize(file.getSize());
			photo.setFileName(file.getOriginalFilename());
		
			return productPhotoModelAssembler.toModel(productPhotoCatalogService.save(photo, file.getInputStream()));
		
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductPhotoModel showPhoto(@PathVariable Long restaurantId, @PathVariable Long productId) {
//		restaurantRegistrationService.findRestaurantByIdOrElseThrow(restaurantId);
//		Product product = productRegistrationService.findProductByIdAndRestaurantOrElseThrow(restaurantId, productId);
		
		ProductPhoto photo = productPhotoCatalogService.findPhotoByRestaurantIdAndProductIdOrElseThrow(restaurantId, productId);
		
		return productPhotoModelAssembler.toModel(photo);
	}
	
	@GetMapping
	public ResponseEntity<InputStreamResource> downloadPhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			ProductPhoto productPhoto = productPhotoCatalogService.findPhotoByRestaurantIdAndProductIdOrElseThrow(restaurantId, productId);
			
			MediaType mediaTypeProductPhoto = MediaType.parseMediaType(productPhoto.getContentType());
			List<MediaType> mediaTypesAcceptedByClient = MediaType.parseMediaTypes(acceptHeader);
			
			checkMediaTypeCompatibility(mediaTypeProductPhoto, mediaTypesAcceptedByClient);
			
			PhotoRecover  photoRecover = productPhotoStorageService.recover(productPhoto.getFileName());
			
			if (photoRecover.hasUrl()) {
				String photoUrl = photoRecover.getUrl();
				
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, photoUrl)
						.build();
				
			} else {
				InputStream photoInputStream = photoRecover.getInputStream();
				
				return ResponseEntity.ok()
						.contentType(mediaTypeProductPhoto)
						.body(new InputStreamResource(photoInputStream));
			}
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long restaurantId, @PathVariable Long productId) {
		productPhotoCatalogService.delete(restaurantId, productId);
	}
	
	private void checkMediaTypeCompatibility(MediaType mediaTypeProductPhoto,
			List<MediaType> mediaTypesAcceptedByClient) throws HttpMediaTypeNotAcceptableException {
		
		boolean compatibility = mediaTypesAcceptedByClient.stream()
				.anyMatch(mediaTypeAcceptedByClient -> mediaTypeAcceptedByClient.isCompatibleWith(mediaTypeProductPhoto));
		
		if (!compatibility) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAcceptedByClient);
		}
	}

	//método simples para salvar direto no localstorage
	//metódo usado para salvar a foto em storage local -> na área de trabalho
	public void savePhotoLocalStorage(MultipartFile multipartFile) {
		var nameFile = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
		
		var filePhoto = Path.of("/home/glauber/Área\sde\sTrabalho/upload-algafood", nameFile);
		
		try {
			multipartFile.transferTo(filePhoto);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
