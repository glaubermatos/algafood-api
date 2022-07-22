package com.algaworks.glauber.algafood.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.glauber.algafood.core.storage.StorageProperties;
import com.algaworks.glauber.algafood.domain.service.ProductPhotoStorageService;

//@Service
public class LocalProductPhotoStorageServiceImpl implements ProductPhotoStorageService {
	
//	@Value("${algafood.storage.local.photo-directory}")
//	private Path pathPhotoDirectory;
	
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void store(NewPhoto photo) {
		try {
			Path filtePath = getFilePath(photo.getFileName());
			
			FileCopyUtils.copy(photo.getInputStream(), 
					Files.newOutputStream(filtePath));
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo", e);
		}
	}
	
	@Override
	public PhotoRecover recover(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
		
			//Testar essa implementação
//			InputStream inputStream = ProductPhotoLocalStorageServiceImpl.class.getResourceAsStream(filePath.toString());
			
			InputStream inputStream = Files.newInputStream(filePath);
			
			PhotoRecover photoRecover = new PhotoRecover();
			photoRecover.setInputStream(inputStream);
		
			
			return photoRecover;
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar arquivo", e);
		}
		
	}
	
	@Override
	public void remove(String fileName) {
		try {
			Path filtePath = getFilePath(fileName);
			
			Files.deleteIfExists(filtePath);
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo", e);
		}
	}
	
	private Path getFilePath(String fileName) {
		return storageProperties.getLocal().getPhotoDirectory()
				.resolve(Path.of(fileName));
	}

}
