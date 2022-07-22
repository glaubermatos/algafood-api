package com.algaworks.glauber.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.glauber.algafood.core.storage.StorageProperties.StorageType;
import com.algaworks.glauber.algafood.domain.service.ProductPhotoStorageService;
import com.algaworks.glauber.algafood.infrastructure.service.storage.LocalProductPhotoStorageServiceImpl;
import com.algaworks.glauber.algafood.infrastructure.service.storage.S3ProductPhotoStorageServiceImpl;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class StorageConfig {
	
	@Autowired
	private StorageProperties storageProperties;

	@Bean
	@ConditionalOnProperty(name = "algafood.storage.type", havingValue = "s3")
	public AmazonS3 amazoS3() {
		var credentials = new BasicAWSCredentials(
				storageProperties.getS3().getAwsAccessKeyId(), 
				storageProperties.getS3().getAwsSecreteAccessKey());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegion())
				.build();
	}
	
	@Bean
	public ProductPhotoStorageService photoStorageService() {
		if (StorageType.S3.equals(storageProperties.getType())) {
			return new S3ProductPhotoStorageServiceImpl();
		} else {
			return new LocalProductPhotoStorageServiceImpl();
		}
	}
}
