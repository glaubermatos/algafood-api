package com.algaworks.glauber.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.ProductPhotoNotFoundException;
import com.algaworks.glauber.algafood.domain.model.ProductPhoto;
import com.algaworks.glauber.algafood.domain.repository.ProductRepository;
import com.algaworks.glauber.algafood.domain.service.ProductPhotoStorageService.NewPhoto;
import com.algaworks.glauber.algafood.domain.service.ProductPhotoStorageService.PhotoRecover;

@Service
public class ProductPhotoCatalogService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductPhotoStorageService productPhotoStorageService;
	
	@Transactional
	public ProductPhoto save(ProductPhoto photo, InputStream inputStream) {
		Long restaurantId = photo.getRestaurantId();
		Long productId = photo.getProduct().getId();
		
		String originalFileName = photo.getFileName();		
		String newFileName = productPhotoStorageService.generateNewFileName(originalFileName);
		String fileNameExisting = null;
		 
		//excluir foto se existir
		Optional<ProductPhoto> photoOptional = productRepository.findPhotoByRestaurantAndProduct(restaurantId, productId);
		
		if (photoOptional.isPresent()) {
			var existingPhoto = photoOptional.get();
			productRepository.delete(existingPhoto);
			fileNameExisting = existingPhoto.getFileName();
		}

		//tenta salvar a foto primeiro
		photo.setFileName(newFileName); //muda o nome da foto concatenando um UUID ao nome
		photo = productRepository.save(photo);
		productRepository.flush();//descarrega tudo que esta na fila do JPA
		
		NewPhoto newPhoto = new NewPhoto();
		newPhoto.setInputStream(inputStream);
		newPhoto.setFileName(photo.getFileName());
		newPhoto.setContentType(photo.getContentType());

		//caso lança alguma exception será feito o rollback no banco de dados devido ao @Transational
		productPhotoStorageService.replace(fileNameExisting, newPhoto);
		
		return photo;
	}
	
	public PhotoRecover recover(String fileName) {
		return productPhotoStorageService.recover(fileName);
	}
	
	@Transactional
	public void delete(Long restaurantId, Long productId) {
		ProductPhoto productPhoto = findPhotoByRestaurantIdAndProductIdOrElseThrow(restaurantId, productId);
		
		productRepository.delete(productPhoto);
		productRepository.flush();
		
		productPhotoStorageService.remove(productPhoto.getFileName()); 
	}
	
	public ProductPhoto findPhotoByRestaurantIdAndProductIdOrElseThrow(Long restaurantId, Long productId) {
		return productRepository.findPhotoByRestaurantAndProduct(restaurantId, productId)
				.orElseThrow(() -> new ProductPhotoNotFoundException(restaurantId, productId));
	}
}
