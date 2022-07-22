package com.algaworks.glauber.algafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.glauber.algafood.core.storage.StorageProperties;
import com.algaworks.glauber.algafood.domain.service.ProductPhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

//@Service
public class S3ProductPhotoStorageServiceImpl implements ProductPhotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public PhotoRecover recover(String fileName) {
		var bucket = storageProperties.getS3().getBucket();
		var keyObject = getPathFile(fileName);
		
		URL url = amazonS3.getUrl(bucket, keyObject);
		
		PhotoRecover photoRecover = new PhotoRecover();
		photoRecover.setUrl(url.toString());
		
		return photoRecover;
	}

	@Override
	public void store(NewPhoto photo) {
		try {
			String pathFile = getPathFile(photo.getFileName());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(photo.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(), 
					pathFile, 
					photo.getInputStream(), 
					objectMetadata)
			.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest); 
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
	}

	@Override
	public void remove(String fileName) {
        try {
        	var bucket = storageProperties.getS3().getBucket();
        	var keyObject = getPathFile(fileName);
        	
        	var deleteObjectRequest = new DeleteObjectRequest(bucket, keyObject);
        	
        	amazonS3.deleteObject(deleteObjectRequest);
        	
        }  catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
	}
	
	private String getPathFile(String fileName) {
		return String.format("%s/%s", storageProperties.getS3().getPhotoDirectory(), fileName);
	}

}
