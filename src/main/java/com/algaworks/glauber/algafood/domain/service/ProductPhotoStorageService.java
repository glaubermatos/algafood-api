package com.algaworks.glauber.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

public interface ProductPhotoStorageService {
	
	PhotoRecover recover(String fileName);

	void store(NewPhoto photo);
	
	default String generateNewFileName(String originalFileName) {
		return UUID.randomUUID().toString() + "_" + originalFileName;
	}
	
	void remove(String fileName);
	
	default void replace(String fileNameExisting, NewPhoto newPhoto) {
		this.store(newPhoto);
		
		if (fileNameExisting != null) {
			this.remove(fileNameExisting);
		}
	}
	
	class NewPhoto {
		
		private String fileName;
		private InputStream inputStream;
		private String contentType;
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public InputStream getInputStream() {
			return inputStream;
		}
		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		
	}
	
	class PhotoRecover {
		
		private InputStream inputStream;
		private String url;
		
		public InputStream getInputStream() {
			return inputStream;
		}
		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		public boolean hasUrl() {
			return url != null;
		}
		
		public boolean hasInputStream() {
			return inputStream != null;
		}
		
	}
}
