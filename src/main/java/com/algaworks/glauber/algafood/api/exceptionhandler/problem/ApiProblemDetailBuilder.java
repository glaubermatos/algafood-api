package com.algaworks.glauber.algafood.api.exceptionhandler.problem;

import java.time.OffsetDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.glauber.algafood.api.exceptionhandler.problem.ApiProblemDetail.Field;

public class ApiProblemDetailBuilder {

	private ApiProblemDetail instance;
	
	public ApiProblemDetailBuilder() {
		this.instance = new ApiProblemDetail();
	}
	
	public ApiProblemDetailBuilder status(Integer status) {
		this.instance.setStatus(status);
		
		return this;
	}
	
	public ApiProblemDetailBuilder type(String type) {
		this.instance.setType(type);
		
		return this;
	}
	
	public ApiProblemDetailBuilder title(String title) {
		this.instance.setTitle(title);
		
		return this;
	}
	
	public ApiProblemDetailBuilder detail(String detail) {
		this.instance.setDetail(detail);
		
		return this;
	}
	
	public ApiProblemDetailBuilder userMessage(String userMessage) {
		this.instance.setUserMessage(userMessage);
		
		return this;
	}
	
	public ApiProblemDetailBuilder timestamp(OffsetDateTime timestamp) {
		this.instance.setTimestamp(timestamp);
		
		 return this;
	}
	
	public ApiProblemDetailBuilder fields(List<Field> fieldsError) {
		this.instance.setFields(fieldsError);
		
		return this;
	}
	
	public ApiProblemDetail build() {
		if (StringUtils.isBlank(this.instance.getUserMessage())) {
			throw new IllegalStateException("A propriedade ApiProblemDetail.userMessage não pode ser nula. Deve ser preenchida com a mensagem lançadas pelas exceções tratadas");
		}
		
		return this.instance;
	}
}
