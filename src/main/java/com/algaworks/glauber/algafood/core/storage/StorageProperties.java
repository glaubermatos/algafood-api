package com.algaworks.glauber.algafood.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {
	
	private Local local = new Local();
	private S3 s3 = new S3();
	private StorageType type = StorageType.LOCAL;

	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}
	public S3 getS3() {
		return s3;
	}
	public void setS3(S3 s3) {
		this.s3 = s3;
	}
	public StorageType getType() {
		return type;
	}
	public void setType(StorageType type) {
		this.type = type;
	}

	public enum StorageType {
		LOCAL, S3;
	}

	public class Local {
		
		private Path photoDirectory;

		public Path getPhotoDirectory() {
			return photoDirectory;
		}
		public void setPhotoDirectory(Path photoDirectory) {
			this.photoDirectory = photoDirectory;
		}		
	}

	public class S3 {
		
		private String awsAccessKeyId;
		private String awsSecreteAccessKey;
		private String bucket;
		private String region;
		private String photoDirectory;
		
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getBucket() {
			return bucket;
		}
		public void setBucket(String bucket) {
			this.bucket = bucket;
		}
		public String getAwsAccessKeyId() {
			return awsAccessKeyId;
		}
		public void setAwsAccessKeyId(String awsAccessKeyId) {
			this.awsAccessKeyId = awsAccessKeyId;
		}
		public String getAwsSecreteAccessKey() {
			return awsSecreteAccessKey;
		}
		public void setAwsSecreteAccessKey(String awsSecreteAccessKey) {
			this.awsSecreteAccessKey = awsSecreteAccessKey;
		}
		public String getPhotoDirectory() {
			return photoDirectory;
		}
		public void setPhotoDirectory(String photoDirectory) {
			this.photoDirectory = photoDirectory;
		}	
		
	}
}
