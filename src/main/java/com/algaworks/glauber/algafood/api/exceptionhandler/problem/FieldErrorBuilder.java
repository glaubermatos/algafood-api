package com.algaworks.glauber.algafood.api.exceptionhandler.problem;

import com.algaworks.glauber.algafood.api.exceptionhandler.problem.ApiProblemDetail.Field;

public class FieldErrorBuilder {

	private Field instance;
	
	public FieldErrorBuilder() {
		this.instance = new ApiProblemDetail.Field();
	}
	
	public FieldErrorBuilder name(String fieldName) {
		this.instance.setName(fieldName);
		
		return this;
	}
	
	public FieldErrorBuilder userMessage(String errorMessage) {
		this.instance.setUserMessage(errorMessage);
				
		return this;
	}
	
	public Field build() {
		return this.instance;
	}
}
