package com.algaworks.glauber.algafood.core.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private Set<MediaType> allowedContentTypes;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		Set<MediaType> setMediaTypes = new HashSet<>();
		
		for (String mediaTypeString : constraintAnnotation.allowed()) {
			setMediaTypes.add(MediaType.parseMediaType(mediaTypeString));
		}
		
		this.allowedContentTypes = setMediaTypes;
	}
	
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		MediaType mediaType = MediaType.parseMediaType(multipartFile.getContentType());
		
		return multipartFile == null || allowedContentTypes.contains(mediaType);
	}
	
	//Implementação do professor
//	private List<String> allowedContentTypes;
//    
//    @Override
//    public void initialize(FileContentType constraint) {
//        this.allowedContentTypes = Arrays.asList(constraint.allowed());
//    }
//    
//    @Override
//    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
//        return multipartFile == null 
//                || this.allowedContentTypes.contains(multipartFile.getContentType());
//    }

}
